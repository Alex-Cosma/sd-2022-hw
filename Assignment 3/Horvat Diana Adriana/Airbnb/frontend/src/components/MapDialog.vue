<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
  <div style="background-color: olivedrab">
    <div style="background-color: olivedrab">
      <h2 style="background-color: olivedrab">Search locations on the map</h2>
      <label>
        <gmap-autocomplete @place_changed="initMarker" style="background-color: #e4eed0; margin-right: 20px; width: 60%"></gmap-autocomplete>
        <button @click="addLocationMarker" style="background-color: olivedrab">Search</button>
      </label>
      <br/>

    </div>
    <br>
    <gmap-map
        :zoom="14"
        :center="center"
        style="width:100%;  height: 400px;"
    >
      <gmap-marker
          :key="index"
          v-for="(m, index) in locationMarkers"
          :position="m.position"
          @click="center=m.position"
      ></gmap-marker>
    </gmap-map>
  </div>
  </v-dialog>
</template>

<script>
export default {
  name: "MapDialog",
  props: {
    item: Object,
    opened: Boolean,
  },

  data() {
    return {
      center: {
        lat: 39.7837304,
        lng: -100.4458825
      },
      locationMarkers: [],
      locPlaces: [],
      existingPlace: null,
    };
  },
  mounted() {
    this.locateGeoLocation();
  },
  methods: {
    initMarker(loc) {
      this.existingPlace = loc;
    },
    addLocationMarker() {
      if (this.existingPlace) {
        const marker = {
          lat: this.existingPlace.geometry.location.lat(),
          lng: this.existingPlace.geometry.location.lng()
        };
        this.locationMarkers.push({position: marker});
        this.locPlaces.push(this.existingPlace);
        this.center = marker;
        this.existingPlace = null;
      }
    },
    locateGeoLocation: function () {
      navigator.geolocation.getCurrentPosition(res => {
        console.log(res);
        this.center = {
          lat: res.coords.latitude,
          lng: res.coords.longitude
        };
      });
    }
  }
}
</script>

<style scoped>

</style>