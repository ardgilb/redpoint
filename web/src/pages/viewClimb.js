import RedpointClient from '../api/RedpointClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewClimb extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addClimbToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addClimbToPage);
        this.header = new Header(this.dataStore);
        console.log("viewclimb constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const uuid = urlParams.get('uuid');
        document.getElementById('climb-name').innerText = "Loading Climb ...";
        const climb = await this.client.getClimb(uuid);
        this.dataStore.set('climb', climb);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new RedpointClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addClimbToPage() {
        const climb = this.dataStore.get('climb');
        if (climb == null) {
            return;
        }

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
     * Method to run when the add song playlist submit button is pressed. Call the MusicPlaylistService to add a song to the
     * playlist.
     */
    async addSong() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const playlist = this.dataStore.get('playlist');
        if (playlist == null) {
            return;
        }

        document.getElementById('add-song').innerText = 'Adding...';
        const asin = document.getElementById('album-asin').value;
        const trackNumber = document.getElementById('track-number').value;
        const playlistId = playlist.id;

        const songList = await this.client.addSongToPlaylist(playlistId, asin, trackNumber, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.set('songs', songList);

        document.getElementById('add-song').innerText = 'Add Song';
        document.getElementById("add-song-form").reset();
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
