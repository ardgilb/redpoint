import SocialNetworkClient from '../api/SocialNetworkClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


class CreateContactProfile extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewContact'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewContact);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.contactClient = new SocialNetworkClient(); // Initialize your contact client
    }

    async submit(event) {
        event.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const sanitizeInput = (inputValue) => {
                    if (inputValue === null || inputValue === undefined || inputValue.trim() === '') {
                        return null;
                    }
                    return inputValue.trim();
        };

        const surname = sanitizeInput(document.getElementById('surname').value);
        const givenName = sanitizeInput(document.getElementById('givenName').value);
        const email = sanitizeInput(document.getElementById('email').value);
        const ageValue = sanitizeInput(document.getElementById('age').value);
        const age = ageValue ? parseInt(ageValue, 10) : 0;
        const height = sanitizeInput(document.getElementById('height').value);
        const weight = sanitizeInput(document.getElementById('weight').value);
        const gender = sanitizeInput(document.getElementById('gender').value);
        const bloodType = sanitizeInput(document.getElementById('bloodType').value);
        const phoneNumber = sanitizeInput(document.getElementById('phoneNumber').value);
        const address = sanitizeInput(document.getElementById('address').value);
        //Handling lists
        const jobsInput = sanitizeInput(document.getElementById('jobs').value);
        const jobs = jobsInput ? jobsInput.split(", ").filter(item => item !== '') : [];
        const friendsInput = sanitizeInput(document.getElementById('friends').value);
        const friends = friendsInput ? friendsInput.split(", ").filter(item => item !== '') : [];
        const enemiesInput = sanitizeInput(document.getElementById('enemies').value);
        const enemies = enemiesInput ? enemiesInput.split(", ").filter(item => item !== '') : [];
        const relativesInput = sanitizeInput(document.getElementById('relatives').value);
        const relatives = relativesInput ? relativesInput.split(", ").filter(item => item !== '') : [];
        const notesInput = sanitizeInput(document.getElementById('notes').value);
        const notes = notesInput ? notesInput.split(", ").filter(item => item !== '') : [];
        const tagsInput = sanitizeInput(document.getElementById('tags').value);
        const tags = tagsInput ? new Set(tagsInput.split(", ").filter(item => item !== '')) : null;
        const socialMediaInput = sanitizeInput(document.getElementById('socialMedia').value);
        const socialMedia = socialMediaInput ? socialMediaInput.split(", ").filter(item => item !== '') : [];

    const contactData = {
        surname,
        givenName,
        email,
        gender,
        age,
        height,
        weight,
        bloodType,
        phoneNumber,
        address,
        jobs,
        friends,
        enemies,
        relatives,
        notes,
        tags,
        socialMedia
    };

    // Add logging before sending data to DynamoDB
    console.log('Contact Data:', contactData);

    const contact = await this.contactClient.createContact(contactData);
    this.dataStore.set('contact', contact);
}
    redirectToViewContact() {
            const contact = this.dataStore.get('contact');
            if (contact != null) {
                window.location.href = `/contact.html?contactId=${contact.contactId}`;
            }
        }

}


const main = async () => {
    const createContactProfile = new CreateContactProfile();
    createContactProfile.mount();
};

window.addEventListener('DOMContentLoaded', main);
