import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the RedpointService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class RedpointClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'search', 'getArea', 'getClimb', 'getAllCommentsForClimb'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the area for the given ID.
     * @param id Unique identifier for a area
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The area's metadata.
     */
    async getArea(uuid, errorCallback) {
        try {
            const response = await this.axiosClient.get(`area/${uuid}`);
            return response.data.area;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
         * Gets the climb for the given ID.
         * @param id Unique identifier for a climb
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The climb's metadata.
         */
    async getClimb(uuid, errorCallback) {
            try {
                const response = await this.axiosClient.get(`climb/${uuid}`);
                return response.data.climb;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
    async addCommentToClimb(climbId, text, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create comments.");
            const response = await this.axiosClient.post(`comments`, {
                climbId: climbId,
                text: text
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.comment;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async addLogbookEntry(climbId, date, notes, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can log ascents.");
            const response = await this.axiosClient.post(`entries`, {
                climbId: climbId,
                date: date,
                notes: notes
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.logbookEntry;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getAllCommentsForClimb(climbId, errorCallback) {
            try {
                const response = await this.axiosClient.get(`comments/${climbId}`, {
                    climbId: climbId
                });
                return response.data.comment;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }
    async deleteComment(commentId, errorCallback){
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete comments.");
            const response = await this.axiosClient.delete(`comments/${commentId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async getUserLogbook(userId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`entries/${userId}`, {
                userId: userId
            });
            return response.data.logbookEntry;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async deleteEntry(climbId, errorCallback){
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete logbook entries.");
            const response = await this.axiosClient.delete(`entries/${climbId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    async updateAscent(climbId, date, notes, errorCallback){
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can delete logbook entries.");
            const response = await this.axiosClient.put(`entries/`,{
                climbId: climbId,
                date: date,
                notes: notes
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Search for a song.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The areas that match the search criteria.
     */
    async search(criteria, errorCallback) {
        try {

            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = encodeURI(criteria);

            const response = await this.axiosClient.get(`areas/${queryString}`);

            return response.data.area;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
