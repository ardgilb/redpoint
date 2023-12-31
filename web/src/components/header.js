import RedpointClient from '../api/RedpointClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLogoutButton', 'createMyLogbookButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new RedpointClient();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const siteTitleContainer = document.createElement('div');
        siteTitleContainer.classList.add('site-title-container');

        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = 'Redpoint';

        siteTitleContainer.appendChild(homeButton);


        return siteTitleContainer;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

            let logbookButton; // Declare logbookButton variable

            if (currentUser) {
                logbookButton = this.createMyLogbookButton(currentUser);
                logbookButton.style.marginRight = '10px';
            }
                        
            if (logbookButton) {
                userInfo.appendChild(logbookButton);
            }
        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createMyLogbookButton(currentUser) {
        const logbookButton = document.createElement('a');
        logbookButton.classList.add('button');
        logbookButton.href = `logbook.html?userId=${currentUser.email}`;
        logbookButton.innerText = 'My Logbook';

        return logbookButton;
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}
