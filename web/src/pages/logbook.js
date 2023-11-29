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
        if(this.dataStore.get('loggedIn')){
            document.getElementById('logbook-header').innerText = "Your Logbook"

        }
        else{
            document.getElementById('logbook-header').innerText = this.dataStore.get('userId') + "'s Logbook"
        }
        const logbookList = document.getElementById('logbook-list');
        logbookList.innerHTML = ''; // Clear existing entries

        const userId = this.dataStore.get('userId');
        try {
            const logbookEntries = await this.client.getUserLogbook(userId);
    
            if (logbookEntries.length > 0) {
                for (const entry of logbookEntries) {
                    await this.renderLogbookEntry(entry);
                }
            } else {
                // Display a message if the logbook is empty
                const emptyMessage = document.createElement('li');
                emptyMessage.textContent = 'No logged ascents yet.';
                logbookList.appendChild(emptyMessage);
            }
        } catch (error) {
            console.error('Error loading logbook:', error);
            // Handle error, display message to the user, etc.
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
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <strong>Climb Name:</strong> <a href="climb.html?uuid=${climb.uuid}">${climb.name}, ${climb.yds}</a>
            <br>
            <strong>Date:</strong> ${entry.date}
            <br>
            <strong>Notes:</strong> ${entry.notes}
        `;
        const isLoggedIn = entry.userId === currentUser.email;

        
        logbookList.appendChild(listItem);

        if (isLoggedIn) {
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.onclick = () => this.deleteEntry(entry.climbId);
            const updateButton = document.createElement('button');
            updateButton.textContent = 'Update';
            updateButton.onclick = () => this.showUpdateAscentModal(entry);

            logbookList.appendChild(deleteButton);
            logbookList.appendChild(updateButton);
        }
    }
    async deleteEntry(climbId) {

        await this.client.deleteEntry(climbId);
        this.loadLogbook();
}
showUpdateAscentModal(entry) {
    const modal = document.getElementById('updateAscentModal');
    const ascentDateInput = document.getElementById('ascentDate');
    const ascentNotesInput = document.getElementById('ascentNotes');

    // Populate the modal with existing entry data
    ascentDateInput.value = entry.date;
    ascentNotesInput.value = entry.notes;

    modal.style.display = 'block';

    // Add a click event listener to the window to close the modal if clicked outside of it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }

    // Add a click event listener to the "Update Ascent" button to update the ascent
    document.getElementById('updateAscentBtn').onclick = () => this.updateAscent(entry);
}
async updateAscent(entry) {
    const ascentDate = document.getElementById('ascentDate').value;
    const ascentNotes = document.getElementById('ascentNotes').value;
    const climbId = entry.climbId;

    const result = await this.client.updateAscent(climbId, ascentDate, ascentNotes)

    // Close the modal after updating
    const modal = document.getElementById('updateAscentModal');
    modal.style.display = 'none';

    // Reload the logbook to reflect the changes
    this.loadLogbook();
}

}

// Main method to create an instance of the Logbook class and mount the page.
const main = async () => {
    const logbook = new Logbook();
    logbook.mount();
};

window.addEventListener('DOMContentLoaded', main);
