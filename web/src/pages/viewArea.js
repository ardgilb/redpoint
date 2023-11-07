import RedpointClient from '../api/RedpointClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view playlist page of the website.
 */
class ViewArea extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addAreaToPage', 'addSongsToPage', 'addSong'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addAreaToPage);
        this.header = new Header(this.dataStore);
        console.log("viewarea constructor");
    }

    /**
     * Once the client is loaded, get the playlist metadata and song list.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const uuid = urlParams.get('uuid');
        document.getElementById('area-name').innerText = "Loading Area ...";
        const area = await this.client.getArea(uuid);
        this.dataStore.set('area', area);
    }

    /**
     * Add the header to the page and load the MusicPlaylistClient.
     */
    mount() {
        document.getElementById('add-song').addEventListener('click', this.addSong);

        this.header.addHeaderToPage();

        this.client = new RedpointClient();
        this.clientLoaded();
    }

    /**
     * When the playlist is updated in the datastore, update the playlist metadata on the page.
     */
    addAreaToPage() {
        const area = this.dataStore.get('area');
        if (area == null) {
            return;
        }

        document.getElementById('area-name').innerText = area.areaName;
        if(area.children.length != 0){
            let areahtml = '<table><tr><th>Areas</th><th>Song Count</th><th>Tags</th></tr>';
                            for (const child of area.children) {
                                areahtml += `
                                <tr>
                                    <td>
                                        <a href="area.html?uuid=${child.uuid}">${child.areaName}</a>
                                    </td>
                                    <td>${area.songCount}</td>
                                    <td>${area.tags?.join(', ')}</td>
                                </tr>`;
                            }
                            areahtml += '</table>';
                    document.getElementById('children').innerHTML = areahtml;
        }
        if(area.climbs.length != 0) {
            let climbhtml = '<table><tr><th>Climbs</th><th>Rating</th><th>Tags</th></tr>';
                                    for (const climb of area.climbs) {
                                        climbhtml += `
                                        <tr>
                                            <td>
                                                <a href="climb.html?uuid=${climb.uuid}">${climb.name}</a>
                                            </td>
                                            <td>${climb.yds}</td>
                                            <td>${area.tags?.join(', ')}</td>
                                        </tr>`;
                                    }
                                    climbhtml += '</table>';
                            document.getElementById('climbs').innerHTML = climbhtml;
                }
        }


    /**
     * When the songs are updated in the datastore, update the list of songs on the page.
     */
    addSongsToPage() {
        const songs = this.dataStore.get('songs')

        if (songs == null) {
            return;
        }

        let songHtml = '';
        let song;
        for (song of songs) {
            songHtml += `
                <li class="song">
                    <span class="title">${song.title}</span>
                    <span class="album">${song.album}</span>
                </li>
            `;
        }
        document.getElementById('songs').innerHTML = songHtml;
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
    const viewArea = new ViewArea();
    viewArea.mount();
};

window.addEventListener('DOMContentLoaded', main);
