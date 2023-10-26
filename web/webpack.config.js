const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");
const Dotenv = require('dotenv-webpack');

// Get the name of the appropriate environment variable (`.env`) file for this build/run of the app
const dotenvFile = process.env.API_LOCATION ? `.env.${process.env.API_LOCATION}` : '.env';

module.exports = {
  plugins: [
    new CopyPlugin({
      patterns: [
        {
          from: "static_assets", to: "../",
          globOptions: {
            ignore: ["**/.DS_Store"],
          },
        },
      ],
    }),
    new Dotenv({ path: dotenvFile }),
  ],
  optimization: {
    usedExports: true
  },
  entry: {
    createPlaylist: path.resolve(__dirname, 'src', 'pages', 'createPlaylist.js'),
    viewContact: path.resolve(__dirname, 'src', 'pages', 'viewContact.js'),
    viewPlaylist: path.resolve(__dirname, 'src', 'pages', 'viewPlaylist.js'),
    searchPlaylists: path.resolve(__dirname, 'src', 'pages', 'searchPlaylists.js'),
    createGroup: path.resolve(__dirname, 'src', 'pages', 'createGroup.js'),
    viewGroup: path.resolve(__dirname, 'src', 'pages', 'viewGroup.js'),
    allContacts: path.resolve(__dirname, 'src', 'pages', 'allContacts.js'),
    getAllGroups: path.resolve(__dirname, 'src', 'pages', 'getAllGroups.js'),
    createContact: path.resolve(__dirname, 'src', 'pages', 'createContact.js'),
    test: path.resolve(__dirname, 'src', 'pages', 'test.js'),
  },
  output: {
    path: path.resolve(__dirname, 'build', 'assets'),
    filename: '[name].js',
    publicPath: '/assets/'
  },
  devServer: {
    static: {
      directory: path.join(__dirname, 'static_assets'),
    },
    port: 8000,
    client: {
      // overlay shows a full-screen overlay in the browser when there are js compiler errors or warnings
      overlay: true,
    },
  }
}
