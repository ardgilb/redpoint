import Authenticator from '../api/authenticator';
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
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage', 'addCommentsToPage', 'addComment', 'deleteComment' ], this);
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
        document.getElementById('comments-container').innerHTML = '<h2>Loading Comments...</h2>';
        const climb = await this.client.getClimb(uuid);
        this.dataStore.set('climb', climb);
        this.addClimbToPage(climb)
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
    addClimbToPage(climb) {
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
        const currentUser = await this.client.getIdentity();
        if (currentUser === undefined) {
            errorMessageDisplay.innerText = 'You must be logged in to create a comment.';
            errorMessageDisplay.classList.remove('hidden');
            return;
        }

        document.getElementById('comment-button').innerText = 'Creating Comment...';
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
        const currentUser = await this.client.getIdentity();
        const comments = this.dataStore.get('comments');
        if(comments == undefined){
            return;
        }
        commentsContainer.innerHTML = '<h2>Comments</h2>';
        if(comments.length != 0){
        comments.forEach(comment => {
            const commentElement = document.createElement('div');
            const date = new Date(comment.timeStamp);
            const dateString = date.toDateString() 
            commentElement.innerHTML = `<strong>${comment.userId}</strong>: ${comment.text} (${dateString})`;
            let isAuthor = false;
            if(currentUser != undefined){
                 isAuthor = comment.userId === currentUser.email;
            }


        if (isAuthor) {
            const deleteButton = document.createElement('button');
            deleteButton.textContent = 'Delete';
            deleteButton.style.backgroundColor = 'var(--tertiary-color)'; // Set background color to orange
            deleteButton.style.color = 'var(--secondary-color)'; // Set text color to light gray
            deleteButton.style.border = 'none';
            deleteButton.style.padding = '5px 7px';
            deleteButton.style.marginLeft = 'auto';
            deleteButton.onclick = () => {
                this.showDeleteCommentModal(comment);
            }
            deleteButton.style.marginLeft = '10px';
            commentElement.appendChild(deleteButton);
        }
            commentElement.style.marginBottom = '10px';
            commentsContainer.appendChild(commentElement);
            });
        }
        else{
        const commentElement = document.createElement('div');
        commentElement.innerHTML = `<strong>No Comments</strong>`;
        commentsContainer.appendChild(commentElement);
        }
    }

    async deleteComment(commentId) {
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = '';
            errorMessageDisplay.classList.add('hidden');

        await this.client.deleteComment(commentId);
        const comments = await this.client.getAllCommentsForClimb(this.dataStore.get('climb').uuid)
        this.dataStore.set('comments', comments)
        this.showSuccessMessage("Comment deleted successfully!");
    }
    showDeleteCommentModal(comment) {
        const modal = document.getElementById('deleteModal');
        const yesButton = document.getElementById('yesButton');
        const noButton = document.getElementById('noButton');
    
        modal.style.display = 'block';
    
    
        yesButton.onclick = async () => {
            yesButton.innerText = "Deleting...";
            await this.deleteComment(comment.commentId);
            modal.style.display = 'none';
            yesButton.innerText = "Yes";
        }
        document.getElementById('noButton').onclick = () => modal.style.display = 'none';
    
    }
    async showLogAscentModal() {
        const errorMessageDisplay = document.getElementById('ascent-error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
        const currentUser = await this.client.getIdentity();
        if (currentUser === undefined) {
            errorMessageDisplay.innerText = 'You must be logged in to log an ascent.';
            errorMessageDisplay.classList.remove('hidden');
            setTimeout(() => {
                errorMessageDisplay.classList.add('hidden');
            }, 2000); 
            return;
        }
        const modal = document.getElementById('logAscentModal');
        modal.style.display = 'block';

        window.onclick = function(event) {
            if (event.target === modal) {
            modal.style.display = 'none';
            }
        }
        document.getElementById('closeModal').onclick = function() {
            modal.style.display = 'none';
        }
}

async saveAscent() {
    document.getElementById('saveAscentBtn').innerHTML = "Saving..."
    const climb = this.dataStore.get('climb');

    const ascentDate = document.getElementById('ascentDate').value;
    const ascentNotes = document.getElementById('ascentNotes').value;
    const climbId = climb.uuid;

    try {
        const ascent = await this.client.addLogbookEntry(climbId, ascentDate, ascentNotes, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.showSuccessMessage("Ascent logged successfully!");

        const modal = document.getElementById('logAscentModal');
        modal.style.display = 'none';
    } catch (error) {
        console.error(error);
    }
    document.getElementById('saveAscentBtn').innerHTML = "Save Ascent"

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

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewClimb = new ViewClimb();
    viewClimb.mount();
};

window.addEventListener('DOMContentLoaded', main);
