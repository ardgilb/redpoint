import SocialNetworkClient from '../api/SocialNetworkClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view contacts page of the website.
 */
class ViewContact extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addContactToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addContactToPage);
        this.header = new Header(this.dataStore);
        console.log("viewcontact constructor");
    }
/**
     * Once the client is loaded, get the contact metadata.
     */
    async clientLoaded() {
        try{
            const urlParams = new URLSearchParams(window.location.search);
            const contactId = urlParams.get('contactId');
            document.getElementById('contact-name').innerText = "Loading Contact ...";
            const contact = await this.client.getContact(contactId);
            this.dataStore.set('contact', contact);
        } catch (error) {
            console.error('Error loading contact:', error);
            this.dataStore.set('contact', null);
        }
    }

    /**
     * Add the header to the page and load the SocialNetworkClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new SocialNetworkClient();
        this.clientLoaded();
    }

    /**
     * When the contact is updated in the datastore, update the contact metadata on the page.
     */
   async addContactToPage() {
      try{
          const contact = this.dataStore.get('contact');
          const contactNameElement = document.getElementById('contact-name');

          if (contact) {
             const fullName = [contact.givenName, contact.surname].filter(Boolean).join(' ');
             document.getElementById('contact-name').innerText = fullName || 'Contact Name';
             // Update other HTML elements with contact data as needed
             document.getElementById('contact-surname').innerText = contact.surname || 'unknown';
             document.getElementById('contact-givenName').innerText = contact.givenName || 'unknown';
             document.getElementById('contact-age').innerText = contact.age || 'unknown';
             document.getElementById('contact-height').innerText = contact.height || 'unknown';
             document.getElementById('contact-bloodType').innerText = contact.bloodType || 'unknown';
             document.getElementById('contact-phoneNumber').innerText = contact.phoneNumber || 'unknown';
             document.getElementById('contact-address').innerText = contact.address || 'unknown';
             document.getElementById('contact-gender').innerText = contact.gender || 'unknown';
             document.getElementById('contact-email').innerText = contact.email || 'unknown';
             document.getElementById('contact-addressConfirmed').innerText = contact.addressConfirmed || 'unknown';

             this.populateList('job-history-list', contact.jobs);
             this.populateList('notes-list', contact.notes);
             this.populateList('social-media-list', contact.socialMedia);
             this.populateList('relatives-list', contact.relatives);
             this.populateList('friends-list', contact.friends);
             this.populateList('contact-tags-list', contact.contactTags);
          } else {
             // Handle the case where the contact was not found
              document.getElementById('contact-name').innerText = "Contact not found";
             // Clear other HTML elements or display an error message
          }
      } catch (error) {
          // Handle errors, e.g., display an error message
          console.error('Error fetching contact:', error);
          document.getElementById('contact-name').innerText = "Error fetching contact";}
      }

   populateList(listId, items) {
       const listElement = document.getElementById(listId);
       listElement.innerHTML = ''; // Clear existing list items
       if (items && items.length > 0) {
           items.forEach((item) => {
               const listItem = document.createElement('li');
               listItem.innerText = item || 'unknown'; // Use 'unknown' for empty or undefined items
               listElement.appendChild(listItem);
           });
       } else {
           // Handle the case where the list is empty
           const listItem = document.createElement('li');
           listItem.innerText = 'unknown';
           listElement.appendChild(listItem);
       }
   }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewContact = new ViewContact();
    viewContact.mount();
};

window.addEventListener('DOMContentLoaded', main);
