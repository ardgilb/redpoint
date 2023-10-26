import SocialNetworkClient from '../api/SocialNetworkClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewContacts extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addContactsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addContactsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewcontacts constructor");
    }

    /**
     * Once the client is loaded, get the contact list.
     */
    async clientLoaded() {
          const contacts = await this.client.getAllContacts();
          this.dataStore.set('contacts', contacts);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
//        document.getElementById('add-song').addEventListener('click', this.addSong);

        this.header.addHeaderToPage();

        this.client = new SocialNetworkClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addContactsToPage() {
        console.log("addcontactstopage")
        const contacts = this.dataStore.get('contacts');
        if (contacts == null) {
            console.log("null contacts");
            return;
        }
        for(var i = 0; i < contacts.length; i++){
        const contact = contacts[i];
        const phoneNumber = contact.phoneNumber || 'unknown'; // Replace null with an empty string
        const email = contact.email || 'unknown'; // Replace null with an empty string
        const address = contact.address || 'unknown'; // Replace null with an empty string
        const surname = contact.surname || '';
        document.getElementById("contact-list").innerHTML += '<ul>' + '<h2><a href = /contact.html?contactId=' + contact.contactId + '>'
         + contact.givenName + " " + surname + '</a></h2>' + '<dl>'+
        '<dt>' + "Contact Information:" + '</dt>' +
        '<dd>' + "Phone Number: " + phoneNumber + '</dd>' +
        '<dd>' + "Email: " + email + '</dd>' +
        '<dd>' + "Address: " + address + '</dd>' +
        '</dl></ul>'
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewContacts = new ViewContacts();
    viewContacts.mount();
};

window.addEventListener('DOMContentLoaded', main);
