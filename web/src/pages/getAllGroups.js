import SocialNetworkClient from '../api/SocialNetworkClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewGroups extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addGroupsToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addGroupsToPage);
        this.header = new Header(this.dataStore);
        console.log("viewgroups constructor");
    }

    /**
     * Once the client is loaded, get the contact list.
     */
    async clientLoaded() {
          console.log("clientLoaded running")
          const groups = await this.client.getAllGroups();
          this.dataStore.set('groups', groups);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new SocialNetworkClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addGroupsToPage() {
        console.log("addgroupsstopage")
        const groups = this.dataStore.get('groups');
        if (groups == null) {
            console.log("null groups");
            return;
        }
        const groupList = document.getElementById("group-list");
        groupList.innerHTML = ''; // Clear the previous content

        for (let i = 0; i < groups.length; i++) {
            const encodedGroupName = encodeURIComponent(groups[i].name);
            const groupListItem = document.createElement('ul');
            groupListItem.innerHTML = `
                <h2><a href="/group.html?name=${encodedGroupName}">${groups[i].name}</a></h2>
                <dl>
                    <dd>Contact Count: ${groups[i].contactCount}</dd>
                    <dd>Tags: ${groups[i].tags}</dd>
                </dl>
            `;
            groupList.appendChild(groupListItem);
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewGroups = new ViewGroups();
    viewGroups.mount();
};

window.addEventListener('DOMContentLoaded', main);