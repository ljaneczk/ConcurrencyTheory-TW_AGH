let waterfall = require("async/waterfall");
let N = 10;     // number of clients
let K = 4;      // number of chairs in salon
let C = 5;      // number of haircuts to have

function random(a, b) {
    return Math.random() * (b - a) + a;
}

class BarberSalon {
    constructor(numberOfChairs) {
        this.numberOfChairs = numberOfChairs;
        this.state = 0;
        this.queue = [];
        this.currentClient = null;
    }

    isAnySeatEmpty() {
        return (this.queue.length < this.numberOfChairs);
    }

    acquireWaitingRoom(cb) {
        let i = 0;
        let tryToTake = ((waitTime, barberSalon) => {
            setTimeout(() => {
                if (barberSalon.state === 1) {
                    i++;
                    tryToTake(random(1,2) * waitTime, barberSalon);
                }
                else {
                    barberSalon.state = 1;
                    cb();
                }
            }, waitTime);
        });
        tryToTake(1, this);
    }
}

class Barber {
    constructor(barberSalon) {
        this.sleepingState = 0;
        this.barberSalon = barberSalon;
        this.callNextClient();
    }

    wakeBarber() {
        console.log("Barber has been woken up.");
        this.sleepingState  = 0;
        setTimeout(() => {
            this.barberSalon.currentClient.doHaircut();
            this.callNextClient();
        }, random(0.5, 2) * 100);
    }

    callNextClient() {
        this.barberSalon.acquireWaitingRoom(() => {
            if (this.barberSalon.queue.length > 0) {
                this.barberSalon.currentClient = barberSalon.queue[0];
                this.barberSalon.queue.shift();
                this.barberSalon.state = 0;
                setTimeout(() => {
                   this.barberSalon.currentClient.doHaircut();
                   this.callNextClient();
                }, random(1,2) * 100);
            }
            else {
                this.sleepingState = 1;
                this.barberSalon.state = 0;
            }
        });
    }
}

class BarberClient {
    constructor(id, numberOfHaircutsToHave, barber) {
        this.id = id;
        this.numberOfHaircutsToHave = numberOfHaircutsToHave;
        this.numberOfCurrentHaircut = 1;
        this.barber = barber;
        this.barberSalon = this.barber.barberSalon;
    }

    enterBarberSalon() {
        if (this.numberOfCurrentHaircut <= this.numberOfHaircutsToHave) {
            this.barberSalon.acquireWaitingRoom(() => {
               if (this.barber.sleepingState === 1) {
                   this.barber.wakeBarber();
                   this.barberSalon.currentClient = this;
                   this.barberSalon.state = 0;
                   console.log("Client " + this.id + " wakes up barber.");
               }
               else if (this.barberSalon.isAnySeatEmpty()) {
                   this.barberSalon.queue.push(this);
                   this.barberSalon.state = 0;
                   console.log("Client " + this.id + " takes seat in waiting room.");
               }
               else {
                   this.barberSalon.state = 0;
                   console.log("Client " + this.id + " leaves without haircut.");
                   setTimeout(() => { this.enterBarberSalon(); }, random(0.7,3.5) * 100);
               }
            });
        }
    }

    doHaircut() {
        this.numberOfHaircutsToHave--;
        setTimeout(() => this.enterBarberSalon(), random(1,3) * 100);
        console.log("Client " + this.id + " leaves with haircut nr " + this.numberOfCurrentHaircut);
    }
}


let barberSalon = new BarberSalon(K);
let barber = new Barber(barberSalon);
let clients = [];

for (let i = 0; i < N; i++) {
    clients.push(new BarberClient(i,C, barber));
}

for (let i = 0; i < N; i++) {
    clients[i].enterBarberSalon();
}

