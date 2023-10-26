import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class SocialNetworkClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'createGroup', 'getAllGroups', 'getAllContacts', 'getContact', 'createContact', 'addContactToGroup'];
      
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
         * Create a new group owned by the current user.
         * @param name The name of the group to create.
         * @param tags Metadata tags to associate with a group.
         * @param errorCallback (Optional) A function to execute if the call fails.
         * @returns The group that has been created.
        */

        async createGroup(name, tags, errorCallback) {
            try {
                const token = await this.getTokenOrThrow("Only authenticated users can create groups.");
                const response = await this.axiosClient.post(`groups`, {
                    name: name,
                    tags: tags
                }, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                return response.data.group;
            } catch (error) {
                this.handleError(error, errorCallback)
            }
        }
        async createContact(contactData, errorCallback) {
                    try {
                        const token = await this.getTokenOrThrow("Only authenticated users can create contacts.");
                        const response = await this.axiosClient.post(`contacts`, {
                            surname: contactData["surname"],
                            givenName: contactData["givenName"],
                            email: contactData["email"],
                            gender: contactData["gender"],
                            age: contactData["age"],
                            height: contactData["height"],
                            weight: contactData["weight"],
                            bloodType: contactData["bloodType"],
                            phoneNumber: contactData["phoneNumber"],
                            address: contactData["address"],
                            jobs: contactData["jobs"],
                            friends: contactData["friends"],
                            enemies: contactData["enemies"],
                            relatives: contactData["relatives"],
                            notes: contactData["notes"],
                            contactTags: contactData["tags"],
                            socialMedia: contactData["socialMedia"]
                        }, {
                            headers: {
                                Authorization: `Bearer ${token}`
                            }
                        });
                        return response.data.contact;
                    } catch (error) {
                        this.handleError(error, errorCallback)
                    }
                }
        async getAllContacts(errorCallback) {
        try {
                const token = await this.getTokenOrThrow("Only authenticated users can see all contacts.");
                const response = await this.axiosClient.get(`contacts`, {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                return response.data.contact;
            } catch (error) {
                    this.handleError(error, errorCallback)
            }
        }

        async getAllGroups(errorCallback) {
                try {
                        const token = await this.getTokenOrThrow("Only authenticated users can see all groups.");
                        const response = await this.axiosClient.get(`groups`, {
                            headers: {
                                Authorization: `Bearer ${token}`
                            }
                        });
                        return response.data.group;
                    } catch (error) {
                            this.handleError(error, errorCallback)
                    }
                }

    /**
     * Gets the contact for the given userId.
     * @param userId Unique identifier for the requesting user.
     * @param contactId Unique identifier for a contact.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The contact's metadata.
    */
    async getContact(contactId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view contacts.");
            const response = await this.axiosClient.get(`contacts/${contactId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.contact;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Get the contacts on a given group by the group's name.
     * @param name Unique identifier for a group
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of contacts in a group.
    */
    async getGroupContacts(name, errorCallback) {
        try {
            const response = await this.axiosClient.get(`groups/${name}/contacts`);
            return response.data.contactList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Get the contacts in a given group by the group's name.
     * @param name Unique identifier for a group
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of contacts in a group.
    */
    async getGroup(name, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can view contacts.");
            const response = await this.axiosClient.get(`groups/${name}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.group;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }
    /**
     * Method to add a contact to an existing group.
     * @param userId - The user ID.
     * @param contactId - The contact ID to be added.
     * @param groupName - The name of the group.
     * @param errorCallback - Callback function to handle errors.
     * @returns The contact being added to the group.
     */
    async addContactToGroup(groupName, contactId, errorCallback) {
        try {
            const token = await this.getTokenOrThrow(
                "Only authenticated users can add a contact to a group."
            );

            const response = await this.axiosClient.post(
                `groups/${groupName}/contacts`,
                {
                    name: groupName,
                    contactId: contactId
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            return response;
        } catch (error) {
            this.handleError(error, errorCallback);
            throw error;
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
