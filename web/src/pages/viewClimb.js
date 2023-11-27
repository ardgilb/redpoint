import RedpointClient from '../api/RedpointClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view climb page of the website.
 */
class ViewClimb extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage', 'addCommentsToPage', 'addComment', 'deleteComment', ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClimbToPage);
        this.dataStore.addChangeListener(this.addCommentsToPage)
        this.header = new Header(this.dataStore);
        console.log("viewclimb constructor");
    }

    /**
     * Once the client is loaded, get the climb metadata and comment list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const uuid = urlParams.get('uuid');
        document.getElementById('climb-name').innerText = "Loading Climb ...";
        const climb = await this.client.getClimb(uuid);
        this.dataStore.set('climb', climb);
        const comments = await this.client.getAllCommentsForClimb(uuid)
        this.dataStore.set('comments', comments)
    }

    /**
     * Add the header to the page and load the RedpointClient.
     */
    mount() {
        document.getElementById('comment-button').addEventListener('click', this.addComment);
        document.getElementById('log').addEventListener('click', this.showLogAscentModal.bind(this));
        document.getElementById('saveAscentBtn').addEventListener('click', this.saveAscent.bind(this));


        this.header.addHeaderToPage();

        this.client = new RedpointClient();
        this.clientLoaded();
    }

    /**
     * When the climb is updated in the datastore, update the climb metadata on the page.
     */
    addClimbToPage() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            return;
        }
        document.getElementById("title").innerText = climb.name;
        document.getElementById('climb-name').innerText = climb.name + ", " + climb.yds;
        if(climb.content.description != "" && climb.content.description != null){
        document.getElementById('desc').innerText = "Description";
        document.getElementById('description').innerText = climb.content.description;
        }
        if(climb.content.location != "" && climb.content.location != null){
                document.getElementById('loc').innerText = "Location";
                document.getElementById('location').innerText = climb.content.location;
                }
        if(climb.content.protection != "" && climb.content.protection != null){
                document.getElementById('prot').innerText = "Protection";
                document.getElementById('protection').innerText = climb.content.protection;
                }
        }




    /**
     * Method to run when the add comment submit button is pressed. Call the RedpointClient to add a comment to the
     * climb.
     */
    async addComment() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const climb = this.dataStore.get('climb');
        if (climb == null) {
            return;
        }

        document.getElementById('comment').innerText = 'Creating Comment...';
        const text = document.getElementById('comment').value;
        const climbId = climb.uuid;

        const comment = await this.client.addCommentToClimb(climbId, text, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        const comments = await this.client.getAllCommentsForClimb(this.dataStore.get('climb').uuid)
        this.dataStore.set('comments', comments)

        document.getElementById('comment-button').innerText = 'Create Comment';
        document.getElementById('comment').value = '';


    }
    /**
     * Method to run when retreiving all comments for a given climb.
     */
    async addCommentsToPage() {
        const commentsContainer = document.getElementById('comments-container');

        commentsContainer.innerHTML = '<h2>Comments</h2>';
        const comments = this.dataStore.get('comments');
        const currentUser = await this.client.getIdentity();
        if(comments.length != 0){
        comments.forEach(comment => {
            const commentElement = document.createElement('div');
            const date = new Date(comment.timeStamp);
            const dateString = date.toDateString() 
            commentElement.innerHTML = `<strong>${comment.userId}</strong>: ${comment.text} (${dateString})`;
            
            const isAuthor = comment.userId === currentUser.email;

        if (isAuthor) {
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.onclick = () => this.deleteComment(comment.commentId);

            commentElement.appendChild(deleteButton);
        }
            commentsContainer.appendChild(commentElement);
            });
        }
        else{
        const commentElement = document.createElement('div');
        commentElement.innerHTML = `<strong>No Comments</strong>`;
        commentsContainer.appendChild(commentElement);
        }
    }
    /**
     * Method to run when the delete comment button is pressed.
     */
    async deleteComment(commentId) {
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = '';
            errorMessageDisplay.classList.add('hidden');

        await this.client.deleteComment(commentId);
        const comments = await this.client.getAllCommentsForClimb(this.dataStore.get('climb').uuid)
        this.dataStore.set('comments', comments)
    }

// Add a new method to handle the modal display
    showLogAscentModal() {
        const modal = document.getElementById('logAscentModal');
        modal.style.display = 'block';

     // Add a click event listener to the window to close the modal if clicked outside of it
        window.onclick = function(event) {
            if (event.target === modal) {
            modal.style.display = 'none';
            }
        }

    // Add a click event listener to the close button to close the modal
        document.getElementById('closeModal').onclick = function() {
            modal.style.display = 'none';
        }
}

async saveAscent() {
    const climb = this.dataStore.get('climb');

    const ascentDate = document.getElementById('ascentDate').value;
    const ascentNotes = document.getElementById('ascentNotes').value;
    const climbId = climb.uuid;

    try {
        const ascent = await this.client.addLogbookEntry(climbId, ascentDate, ascentNotes, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        // Display success message
        this.showSuccessMessage("Ascent logged successfully!");

        // Close the modal after saving
        const modal = document.getElementById('logAscentModal');
        modal.style.display = 'none';
    } catch (error) {
        // Handle other errors if needed
        console.error(error);
    }
}

showSuccessMessage(message) {
    // Display the success message where you see fit in your UI
    const successMessageElement = document.getElementById('success-message');
    successMessageElement.innerText = message;
    successMessageElement.classList.remove('hidden');

    // Optionally, hide the success message after a certain time
    setTimeout(() => {
        successMessageElement.classList.add('hidden');
    }, 5000); // Hide after 5 seconds (adjust as needed)
}

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewClimb = new ViewClimb();
    viewClimb.mount();
};

window.addEventListener('DOMContentLoaded', main);
