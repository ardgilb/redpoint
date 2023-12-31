import RedpointClient from '../api/RedpointClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class Logbook extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'loadLogbook', 'renderLogbookEntry'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        console.log("logbook constructor");
    }

    async clientLoaded() {
        document.getElementById('logbook-header').innerText = "Loading...";
        const urlParams = new URLSearchParams(window.location.search);
        const userId = urlParams.get('userId');
        this.dataStore.set('userId', userId)
        const currentUser = await this.client.getIdentity();
        const loggedIn = currentUser.email === userId;
        this.dataStore.set('loggedIn', loggedIn);
        await this.loadLogbook();
    } 
    /**
    * Main method to run when the page contents have loaded.
    */
    async mount() {
       this.header.addHeaderToPage();
       this.client = new RedpointClient();
       this.clientLoaded();
   }

    /**
     * Load and render the user's logbook entries.
     */
    async loadLogbook() {
        const logbookList = document.getElementById('logbook-list');
        logbookList.innerHTML = ''; 

        const userId = this.dataStore.get('userId');
        try {
            const logbookEntries = await this.client.getUserLogbook(userId);
    
            if (logbookEntries.length > 0) {
                for (const entry of logbookEntries) {
                    await this.renderLogbookEntry(entry);
                }
            } else {
                const emptyMessage = document.createElement('li');
                emptyMessage.textContent = 'No logged ascents yet.';
                logbookList.appendChild(emptyMessage);
            }
        } catch (error) {
            console.error('Error loading logbook:', error);
        }
        if(this.dataStore.get('loggedIn')){
            document.getElementById('logbook-header').innerText = "Your Logbook"

        }
        else{
            document.getElementById('logbook-header').innerText = this.dataStore.get('userId') + "'s Logbook"
        }
    }

    /**
     * Render a logbook entry and append it to the logbook list.
     * @param {Object} entry - Logbook entry data.
     */
    async renderLogbookEntry(entry) {
        const logbookList = document.getElementById('logbook-list');
        const climb = await this.client.getClimb(entry.climbId);
        const currentUser = await this.client.getIdentity();
        const date = new Date(entry.date);
        const listItem = document.createElement('li');
        
        listItem.innerHTML = `
            <strong>Climb Name:</strong> <a href="climb.html?uuid=${climb.uuid}">${climb.name}, ${climb.yds}</a>
            <br>
            <strong>Date:</strong> ${date.toDateString()}
            <br>
            <strong>Notes:</strong> ${entry.notes}
        `;
        
        const isLoggedIn = entry.userId === currentUser.email;
    
        if (isLoggedIn) {
            const deleteButton = this.createStyledButton('Delete', 'var(--tertiary-color)');
            deleteButton.onclick = () => {
                this.showDeleteAscentModal(entry)
                .catch(error => {
                    console.error('Error deleting entry:', error);
                    deleteButton.textContent = 'Delete';
                });
            }
            const updateButton = this.createStyledButton('Update', 'var(--tertiary-color)');
            updateButton.onclick = () => {
                this.showUpdateAscentModal(entry)
                .catch(error => {
                    console.error('Error updating entry:', error);
                    updateButton.textContent = 'Update';
                });
            }
    
            const space = document.createElement('span');
            space.style.marginRight = '10px';
            
            listItem.appendChild(document.createElement('br')); 
            listItem.appendChild(deleteButton);
            listItem.appendChild(space);
            listItem.appendChild(updateButton);

        }
    
        logbookList.appendChild(listItem);
    }
    
    createStyledButton(text, backgroundColor) {
        const button = document.createElement('button');
        button.textContent = text;
        button.style.backgroundColor = backgroundColor;
        button.style.color = 'var(--secondary-color)';
        button.style.border = 'none';
        button.style.padding = '5px 7px';
        button.style.marginTop = '5px';
    
        return button;
    }
    
    async deleteEntry(climbId) {
        await this.client.deleteEntry(climbId);
        this.showSuccessMessage("Ascent deleted successfully!");

        await this.loadLogbook();
}
showDeleteAscentModal(entry) {
    const modal = document.getElementById('deleteModal');
    const yesButton = document.getElementById('yesButton');
    const noButton = document.getElementById('noButton');

    modal.style.display = 'block';


    yesButton.onclick = async () => {
        yesButton.innerText = "Deleting...";
        await this.deleteEntry(entry.climbId);
        modal.style.display = 'none';
        yesButton.innerText = "Yes";
    }
    document.getElementById('noButton').onclick = () => modal.style.display = 'none';

}
showUpdateAscentModal(entry) {
    const modal = document.getElementById('updateAscentModal');
    const ascentDateInput = document.getElementById('ascentDate');
    const ascentNotesInput = document.getElementById('ascentNotes');

    ascentDateInput.value = entry.date;
    ascentNotesInput.value = entry.notes;

    modal.style.display = 'block';

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }

    document.getElementById('updateAscentBtn').onclick = () => this.updateAscent(entry);
}
async updateAscent(entry) {
    document.getElementById('updateAscentBtn').innerHTML = "Updating...";
    const ascentDate = document.getElementById('ascentDate').value;
    const ascentNotes = document.getElementById('ascentNotes').value;
    const climbId = entry.climbId;

    const result = await this.client.updateAscent(climbId, ascentDate, ascentNotes)

    const modal = document.getElementById('updateAscentModal');
    modal.style.display = 'none';
    document.getElementById('updateAscentBtn').innerHTML = "Update Ascent";
    this.showSuccessMessage("Ascent updated successfully!");

    this.loadLogbook();
}
showSuccessMessage(message) {
    const successMessageElement = document.getElementById('success-message');
    successMessageElement.innerText = message;
    successMessageElement.classList.remove('hidden');
    setTimeout(() => {
        successMessageElement.classList.add('hidden');
    }, 2000); 
}

}

const main = async () => {
    const logbook = new Logbook();
    logbook.mount();
};

window.addEventListener('DOMContentLoaded', main);
