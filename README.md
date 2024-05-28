# example-android-gmaps-polygon-coloring-issue

### Context
This is a PoC for reproducing an issue on the new renderer of the Google Maps Android SDK.

The issue is that when using the new render to draw a GeoJson polygon on the map and then you try changing the color of the polygon if your logic for some reason does multiple changes to the color in a short span of time then the new render draws multiple color "layers" on the map rather than just updating the color of the polygon.

### How to run the App
To run this example application you will need to add a valid Google Maps API key on your local copy of this project on your `local.properties` file like this `MAPS_API_KEY=YOUR_API_KEY`
