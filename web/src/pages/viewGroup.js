import SocialNetworkClient from '../api/SocialNetworkClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view group page of the website.
 */
class ViewGroup extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addGroupToPage', 'addContactsToPage', 'addContact', 'populateContactDropdown'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addGroupToPage);
        this.dataStore.addChangeListener(this.addContactsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewgroup constructor");

        this.addContact = this.addContact.bind(this);
    }

    /**
     * Once the client is loaded, get the group metadata and contact list.
     */
    async clientLoaded() {
        try {
          const urlParams = new URLSearchParams(window.location.search);
          const encodedName = urlParams.get('name');
          const groupName = decodeURIComponent(encodedName);
          document.getElementById('group-name').innerText = 'Loading group ...';
          const group = await this.client.getGroup(groupName);
          this.dataStore.set('group', group);
          const contacts = await this.client.getAllContacts();
          this.dataStore.set('contacts', contacts);
          this.populateContactDropdown();
        } catch (error) {
          console.error('Error loading group:', error);
        }
      }


    /**
     * Add the header to the page and load the SocialNetworkClient.
     */
    mount() {
        document.getElementById('add-contact').addEventListener('click', this.addContact);

        this.header.addHeaderToPage();
        this.client = new SocialNetworkClient();
        this.clientLoaded();
        this.populateContactDropdown();
        }

    /**
     * When the group is updated in the datastore, update the group metadata on the page.
     */
    addGroupToPage() {
        try {
            const group = this.dataStore.get('group');

            document.getElementById('group-name').innerText = group.name || 'N/A';

            let tagHtml = '';
            let tag;
            for (tag of group.tags) {
                tagHtml += '<div class="tag">' + tag + '</div>';
            }
            document.getElementById('group-tags').innerHTML = tagHtml;
        } catch (error) {
            console.error('Error fetching group:', error);
            document.getElementById('group-name').innerText = "Error fetching group";
        }
    }

    /**
     * When the contacts are updated in the datastore, update the list of contacts on the page.
     */
    addContactsToPage() {
      try {
        console.log('addcontactstopage');
        const group = this.dataStore.get('group');
        const contactList = group.contactList || [];

        if (contactList.length === 0) {
          console.log('null contacts');
          document.getElementById('contact-list').innerHTML = 'No contacts available.';
          return;
        }

        const contactListHtml = contactList.map((contact) => `
          <li>
            <h2><a href="/contact.html?contactId=${contact.contactId}">
              ${contact.givenName || ''} ${contact.surname || ''}
            </a></h2>
          </li>
        `).join('');

        document.getElementById('contact-list').innerHTML = `<ul>${contactListHtml}</ul>`;
      } catch (error) {
        console.error('Error adding contacts to page:', error);
      }
    }

    /**
     * Method to run when the add contact group submit button is pressed. Call the SocialNetworkService to add a contact to the group.
     */

     async addContact() {
         try {
             const errorMessageDisplay = document.getElementById('error-message');
             errorMessageDisplay.innerText = '';
             errorMessageDisplay.classList.add('hidden');

             const group = this.dataStore.get('group');
             if (!group) {
                 return;
             }

         const contactId = document.getElementById('contact-dropdown').value;

             if (!contactId) {
                 errorMessageDisplay.innerText = 'Please select a contact.';
                 errorMessageDisplay.classList.remove('hidden');
                 return;
             }
             console.log('Data being sent in Axios request:', {
                 group: group.name,
                 contactId: contactId,
             });

         document.getElementById('add-contact').innerText = 'Adding...';

           // Call the client to add the contact to the group
         const updatedGroup = await this.client.addContactToGroup(group.name, contactId, (error) => {
         errorMessageDisplay.innerText = `Error: ${error.message}`;
         errorMessageDisplay.classList.remove('hidden');
         });
           // Update the group data in the DataStore
         this.dataStore.set('group', updatedGroup);

           // Reset the form and button text
          document.getElementById('add-contact').innerText = 'Add Contact';
          document.getElementById('add-contact-form').reset();

          window.location.reload();
         } catch (error) {
             console.error('Error adding contact:', error);
         }
         }

    async populateContactDropdown() {
        try {
             const contacts = this.dataStore.get('contacts');

             if (!contacts) {
                  console.log("No contacts found in DataStore");
                  return;
             }
            const dropdown = document.getElementById('contact-dropdown');

            contacts.forEach((contact) => {
                const option = document.createElement('option');
                option.value = contact.contactId;

                // Display the contact's name (givenName and surname)
                const fullName = `${contact.givenName || ''} ${contact.surname || ''}`.trim();
                option.textContent = fullName;
                dropdown.appendChild(option);
            });
        } catch (error) {
            console.error('Error fetching contacts:', error);
        }
    }
    }


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewGroup = new ViewGroup();
    viewGroup.mount();
};

window.addEventListener('DOMContentLoaded', main);