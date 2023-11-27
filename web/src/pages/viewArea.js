import RedpointClient from '../api/RedpointClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view area page of the website.
 */
class ViewArea extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addAreaToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addAreaToPage);
        this.header = new Header(this.dataStore);
        console.log("viewarea constructor");
    }

    /**
     * Once the client is loaded, get the area metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const uuid = urlParams.get('uuid');
        document.getElementById('area-name').innerText = "Loading Area ...";
        const area = await this.client.getArea(uuid);
        this.dataStore.set('area', area);
    }

    /**
     * Add the header to the page and load the RedpointClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new RedpointClient();
        this.clientLoaded();
    }

    /**
     * When the area is updated in the datastore, update the area metadata on the page.
     */
    addAreaToPage() {
        const area = this.dataStore.get('area');
        document.getElementById("title").innerText = area.areaName;
        if (area == null) {
            return;
        }
        if(area.content.description != "" && area.content.description != null) {
            document.getElementById("description").innerText = "Description";
            document.getElementById("desc-text").innerText = area.content.description;
        }
        document.getElementById('area-name').innerText = area.areaName;
        if(area.children.length != 0){
            let areahtml = '<table><tr><th>Areas</th></tr>';
                            for (const child of area.children) {
                                areahtml += `
                                <tr>
                                    <td>
                                        <a href="area.html?uuid=${child.uuid}">${child.areaName}</a>
                                    </td>
                                </tr>`;
                            }
                            areahtml += '</table>';
                    document.getElementById('children').innerHTML = areahtml;
        }
        if(area.climbs.length != 0) {
            let climbhtml = '<table><tr><th>Climbs (Left to Right)</th><th>Rating</th></tr>';
                                    for (const climb of area.climbs) {
                                        climbhtml += `
                                        <tr>
                                            <td>
                                                <a href="climb.html?uuid=${climb.uuid}">${climb.name}</a>
                                            </td>
                                            <td>${climb.yds}</td>
                                        </tr>`;
                                    }
                                    climbhtml += '</table>';
                            document.getElementById('climbs').innerHTML = climbhtml;
                }
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
