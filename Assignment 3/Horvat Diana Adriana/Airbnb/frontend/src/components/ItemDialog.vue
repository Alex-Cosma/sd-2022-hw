<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          {{ isNew ? "Create item" : "Accommodation details" }}
        </v-toolbar>
        <v-form>
          <img :src="item.imageURL.picture_url" />
          <v-text-field v-model="item.name" value="item.name" readonly />
          <textarea
            v-model="item.description"
            readonly
            style="width: 600px; height: 200px"
          ></textarea>
          <v-text-field
            v-model="item.room_type"
            value="item.room_type"
            readonly
          />
          <ul>
            Amenities:
            <li v-for="amenity in item.amenities" :key="amenity">
              {{ amenity.amenity }}
            </li>
          </ul>
          <v-date-picker
            style="margin-top: 50px; margin-left: 15px"
            :disabled-dates="[new Date(2022, 5, 20)]"
            v-model="item.dates"
            range
            :min="new Date().toISOString().substr(0, 10)"
          ></v-date-picker>
          <br />
          <label>Price/night ($)</label>
          <v-text-field v-model="item.price" readonly />
          <label>Reviews</label>
          <textarea
            v-for="review in item.reviews"
            :key="review"
            readonly
            style="width: 600px; height: 50px; margin-top: 20px"
            v-model="review.comments"
          >
          </textarea>
        </v-form>
        <v-card-actions>
          <v-btn @click="persist">
            {{ isNew ? "Create" : "Book accommodation" }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>
<script>
import api from "../api";
// import $Scriptjs from "scriptjs";
// import * as VueGoogleMaps from "vue2-google-maps";

const auth_user = JSON.parse(localStorage.getItem("user"));

export default {
  name: "ItemDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  // mounted: function () {
  //   // use your own key...
  //   $Scriptjs(
  //     "https://maps.googleapis.com/maps/api/js?key=AIzaSyBo0wYKBT4jc4ephu_yff6cfnbm3pJsNJw&libraries=geometry,places",
  //     () => {
  //       this.initMap();
  //     }
  //   );
  // },
  methods: {
    persist() {
      if (this.isNew) {
        console.log("create booking", this.item);
        api.items
          .bookAccommodation({
            accommodation_id: this.item.id,
            guest_id: auth_user.id,
            start_date: this.item.dates[0],
            end_date: this.item.dates[1],
          })
          .then(() => this.$emit("refresh"));
      } else {
        console.log("edit");
        api.items
          .bookAccommodation({
            accommodation_id: this.item.id,
            guest_id: auth_user.id,
            start_date: this.item.dates[0],
            end_date: this.item.dates[1],
          })
          .then(() => this.$emit("refresh"));
      }
    },
    // initMap: function () {
    //   console.log("init map");
    //   if(VueGoogleMaps){
    //     console.log("VueGoogleMaps", VueGoogleMaps.gmapApi);
    //     // eslint-disable-next-line no-undef
    //     this.map = new google.maps.Map(
    //         document.getElementById("map"),
    //         {
    //           center: { lat: -34.397, lng: 150.644 },
    //           zoom: 8,
    //         }
    //     );
    //     console.log(this.map);
    //   }
    // },

    disableDates(item) {
      return {
        disabledDates: {
          ranges: [
            {
              from: new Date(
                parseInt(item.bookings[0].start_date.split("-", 3)[0]),
                parseInt(item.bookings[0].start_date.split("-", 3)[1]),
                parseInt(item.bookings[0].start_date.split("-", 3)[2])
              ),
              to: new Date(
                parseInt(item.bookings[0].end_date.split("-", 3)[0]),
                parseInt(item.bookings[0].end_date.split("-", 3)[1]),
                parseInt(item.bookings[0].end_date.split("-", 3)[2])
              ),
            },
          ],
        },
      };
    },
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};

// var state = {
//   disabledDates: {
//     ranges: [{ // Disable dates in given ranges (exclusive).
//       from: new Date(2016, 11, 25),
//       to: new Date(2016, 11, 30)
//     }, {
//       from: new Date(parseInt(this.selectedItem.bookings[0].start_date.split('-',3)[0]), parseInt(this.selectedItem.bookings[0].start_date.split('-',3)[1]), parseInt(this.selectedItem.bookings[0].start_date.split('-',3)[2])),
//         to: new Date(parseInt(this.selectedItem.bookings[0].end_date.split('-',3)[0]), parseInt(this.selectedItem.bookings[0].end_date.split('-',3)[1]), parseInt(this.selectedItem.bookings[0].end_date.split('-',3)[2]))
//     }],
//
//   }
// }
</script>

<style scoped>
#map {
  width: 90vw;
  height: 90vh;
}
</style>
