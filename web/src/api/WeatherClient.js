class WeatherClient {
    constructor() {
        this.baseUrl = 'https://api.weather.gov/';
    }

    async getWeather(latitude, longitude) {
        let forecast = '';
        try {
            const url = `${this.baseUrl}points/${latitude},${longitude}`;
            const response = await fetch(url, {
                headers: {
                    'User-Agent': 'Redpoint/1.0 (ardenbgilbert@gmail.com)',
                    'Accept': 'application/geo+json',
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            forecast = data.properties.forecast;
        } catch (error) {
            console.error('Error fetching weather data:', error);
            throw error;
        }
        try {
            const response = await fetch(forecast, {
                headers: {
                    'User-Agent': 'Redpoint/1.0 (ardenbgilbert@gmail.com)'
                }
            })
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            const array = data.properties.periods;
            return array;
        } catch (error) {
            console.error('Error fetching weather data:', error);
            throw error;
        }
    }
}
export default WeatherClient;
