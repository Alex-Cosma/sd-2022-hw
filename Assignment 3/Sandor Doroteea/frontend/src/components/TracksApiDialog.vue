<template>
  <v-dialog
    transition="dialog-bottom-transition"
    max-width="600"
    :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark> </v-toolbar>
        <v-form>
          <v-text-field v-model="track.id" label="Playlist id" />
          <v-text-field v-model="comment.content" label="Content" />
        </v-form>
        <v-card-actions>
          <v-btn @click="addToPlaylist">Add to playlist</v-btn>
          <v-btn @click="addComment">Add comment</v-btn>
        </v-card-actions>
        <div>
          <stripe-checkout
            ref="checkoutRef"
            mode="payment"
            :pk="publishableKey"
            :line-items="lineItems"
            :success-url="successURL"
            :cancel-url="cancelURL"
            @loading="(v) => (loading = v)"
          />
          <v-btn @click="submit">Buy</v-btn>
        </div>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";
import { StripeCheckout } from "@vue-stripe/vue-stripe";

export default {
  name: "TracksApiDialog",
  components: { StripeCheckout },
  props: {
    track: Object,
    opened: Boolean,
  },
  data() {
    this.publishableKey =
      "pk_test_51KzStYBeCTO9xhNT27bFgNkPT3oiGKBwgVa0h0uBrq2NFcoYD8DiR8nGzbYKA9VcDfqSeX1KGC2zTDaPROTlToEt00AjJnedIi";
    return {
      loading: false,
      lineItems: [
        {
          price: "price_1L0D5TBeCTO9xhNTTCCUuO7j",
          quantity: 1,
        },
      ],
      successURL: "http://localhost:8091/#/payment/success",
      cancelURL: "http://localhost:8091/#/payment/fail",

      comment: [],
      payment: [],
    };
  },
  methods: {
    addToPlaylist() {
      api.tracksApi
        .addToPlaylist({
          id: this.track.id,
          title: this.track.title,
          link: this.track.link,
          duration: this.track.duration,
          explicit_lyrics: this.track.explicit_lyrics,
          preview: this.track.preview,
          artist: this.track.artist,
          album: this.track.album,
        })
        .then(() => this.$emit("refresh"));
    },
    save() {
      api.payment.save({
        name: this.track.title,
        user_id: this.$store.getters["auth/getUserID"],
        track_id: this.track.id,
      });
    },
    purchaseTrack() {
      api.tracksApi
        .purchaseTrack({
          id: this.$store.getters["auth/getUserID"],
          title: this.track.title,
          link: this.track.link,
          duration: this.track.duration,
          explicit_lyrics: this.track.explicit_lyrics,
          preview: this.track.preview,
          artist: this.track.artist,
          album: this.track.album,
        })
        .then(() => this.$emit("refresh"));
    },
    submit() {
      this.save();
      this.purchaseTrack();
      this.$refs.checkoutRef.redirectToCheckout();
    },
    addComment() {
      api.tracksApi
        .addComment({
          content: this.comment.content,
          trackId: this.track.id,
          userId: this.$store.getters["auth/getUserID"],
        })
        .then(() => this.$emit("refresh"));
    },
  },
};
</script>

<style scoped></style>
