<template>
  <div class="container">
    <div class="drawer" style="background-color: #C6C6C6">
      <div class="drawer-content">
        <div>
          <img
              :src="imageLink"
              style="border-radius: 50%; width: 10vw; height: 10vw"
          />
        </div>
        <div> {{ details.firstName }}, {{ details.lastName }}</div>
        <div> {{ details.email }}</div>
        <v-divider></v-divider>
        <div style="margin: 5px">
          <v-btn
              :block="true"
              @click="changeViewToCards"
          > Cards
          </v-btn>
        </div>
        <div style="margin: 5px">
          <v-btn
              @click="changeViewToTransfers"
          > Transfers
          </v-btn>
        </div>
        <div style="margin: 5px">
          <v-btn
              @click="changeViewToDeposits"
          > Deposits
          </v-btn>
        </div>
        <div style="margin: 5px">
          <v-btn
              @click="changeViewToContacts"
          > Contacts
          </v-btn>
        </div>
      </div>
    </div>
    <div class="content" style="background-color: #E7E7E7">
      <div
          style="margin: 20px"
      >
        <v-card v-if="view === 'cards'">
          <v-card-title> Cards</v-card-title>
          <v-card-content>
            <v-card
                class="ma-2"
                v-for="card in cards"
                :key="card.number"
            >
              <v-card-title> Card Number: {{ card.number.match(/.{4}/g).join("-") }}</v-card-title>
              <v-container>
                <v-row>
                  <v-col>
                    <div
                        :style="{ backgroundColor: card.cardType.color, width: '200px', height: '100%'}"
                        style="display: flex; justify-content: center; align-items: center"
                    >
                      <div>
                        {{ card.cardType.name }}
                      </div>
                    </div>
                  </v-col>
                  <v-col>
                    <p><strong> CVV: </strong> {{ card.cvv }} </p>
                    <p><strong> Expiration Date: </strong> {{ card.expirationDate }} </p>
                    <p><strong> CurrentAmount: </strong> {{ card.currentAmount }} {{ getCurrencyFromIban(card.iban) }}
                    </p>
                    <p><strong> IBAN: </strong> {{ card.iban }} </p>
                  </v-col>
                </v-row>
              </v-container>
            </v-card>
          </v-card-content>
          <v-container>
            <v-row>
              <v-spacer/>
              <v-select
                  filled
                  v-model="selectedReportType"
                  label="Report Type"
                  :items="reportTypes"
              ></v-select>
              <v-btn
                  style="background-color: var(--primary); color: white"
                  @click="saveCardsReport"
              >
                Download Cards Report
              </v-btn>
            </v-row>
          </v-container>
        </v-card>
        <v-card v-if="view === 'contacts'">
          <v-card-title> Contacts</v-card-title>
          <v-card-content>
            <v-card class="my-3">
              <v-card-title> Contact Request </v-card-title>
              <v-card-content>
                <v-card
                    class="my-2"
                    v-for="pendingContact in myPendingContacts"
                >
                  <v-card-title> {{ pendingContact.firstName }}, {{ pendingContact.lastName }} </v-card-title>
                  <v-card-content>
                    <v-container>
                      <v-row>
                        <div>
                          <img
                              :src="'http://localhost:3077/images/' + pendingContact.profileImageName"
                          />
                        </div>
                        <v-col>
                          <v-select
                              :disabled="true"
                              label="Select IBAN"
                              :items="pendingContact.ibans"
                          >
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col>
                          <p> <span> Email: </span> {{ pendingContact.email }} </p>
                        </v-col>
                        <v-spacer/>
                        <v-col>
                          <v-spacer/>
                          <v-btn color="success"> Accept </v-btn>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-content>
                </v-card>
              </v-card-content>
            </v-card>
            <v-card class="my-3">
              <v-card-title> Contacts </v-card-title>
              <v-select
                class="mx-2"
                :items="myIbans"
                label="Your IBAN for transfer"
                v-model="selectedIbanForTransfer"
              ></v-select>
              <v-card-content>
                <v-card
                    class="my-2"
                    v-for="contact in myContacts"
                >
                  <v-card-title> {{ contact.firstName }}, {{ contact.lastName }} </v-card-title>
                  <v-card-content>
                    <v-container>
                      <v-row>
                        <div>
                          <img
                              :src="'http://localhost:3077/images/' + contact.profileImageName"
                          />
                        </div>
                        <v-col>
                          <v-select
                              v-model="contact.selectedIban"
                              label="Select IBAN"
                              :items="contact.ibans"
                          >
                          </v-select>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col>
                          <p> <span> Email: </span> {{ contact.email }} </p>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-spacer/>
                        <v-btn
                            color="success"
                            @click="startTransfer(contact.selectedIban)"
                        > Send Money </v-btn>
                      </v-row>
                    </v-container>
                  </v-card-content>
                </v-card>
              </v-card-content>
            </v-card>
          </v-card-content>
        </v-card>
        <v-card v-if="view === 'deposits'">
          <v-card-title>
            Deposits
          </v-card-title>
          <v-card-content>

              <v-card
                  class="ma-2"
                  v-for="deposit in deposits"
              >
                <v-card-title> Card Number: {{ deposit.currentAmount }} / {{ deposit.targetAmount }}</v-card-title>
                <v-container>
                  <v-row>
                    <v-col>
                      <div
                          :style="{ backgroundColor: '#c9c9c9', width: '200px', height: '100%'}"
                          style="display: flex; justify-content: center; align-items: center"
                      >
                        <div>
                          {{ deposit.depositType.name }}
                        </div>
                      </div>
                    </v-col>
                    <v-col>
                      <p><strong> createdOn: </strong> {{ deposit.createdOn }} </p>
                      <p><strong> Owner: </strong> {{ deposit.owner.firstName }}, {{ deposit.owner.lastName }} </p>
                      <p><strong> Current Amount: </strong> {{ deposit.currentAmount }} </p>
                      <p><strong> Target Amount: </strong> {{ deposit.targetAmount }} </p>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card>

          </v-card-content>
        </v-card>
        <v-card v-if="view === 'transfers'">
          <v-card-title> Transfers </v-card-title>
          <v-card-content>

          </v-card-content>
        </v-card>
      </div>
    </div>
    <v-overlay
      v-model="sendingMoney"
      :absolute="true"
    >
      <div style="display: flex; justify-content: center; align-items: center">
        <div>
          <v-card width="500px" height="500px">
            <v-card-title> Send Money </v-card-title>
            <v-container class="pa-6">
              <v-row>
                <v-text-field
                    label="From"
                    :disabled="true"
                    v-model="selectedIbanForTransfer"
                ></v-text-field>
              </v-row>
              <v-row>
                <v-text-field
                    label="From"
                    :disabled="true"
                    v-model="toTransferIban"
                ></v-text-field>
              </v-row>
              <v-row>
                <v-text-field
                    label="Amount"
                    type="number"
                    :loading="processingTheTransfer"
                    v-model="transferAmount"
                    :rules="[
                        (value) => !!value || 'The amount should not be empty',
                        (value) => value < cards.find(card => card.iban === selectedIbanForTransfer).currentAmount || 'You exceeded the limit of ' + cards.find(card => card.iban === selectedIbanForTransfer).currentAmount
                    ]"
                >
                </v-text-field>
              </v-row>
              <v-row>
                <v-spacer></v-spacer>
                <v-col>
                  <v-btn
                    class="mx-2"
                    :disabled="processingTheTransfer"
                    @click="sendingMoney = false"
                  > Cancel </v-btn>
                  <v-btn
                      color="success"
                      class="mx-2"
                      :disabled="processingTheTransfer"
                      @click="startTransferProcess"
                  > Send </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </div>
      </div>
    </v-overlay>
  </div>
</template>

<script>
import AuthService from "@/service/AuthService";
import CardsService from "@/service/CardsService";
import ReportsService from "@/service/ReportsService";
import ContactsService from "@/service/ContactsService";
import DepositsService from "@/service/DepositsService";

export default {
  name: "Dashboard",
  data() {
    return {
      drawer: false,
      view: "cards",
      details: {},
      cards: {},
      reportTypes: [
        "CSV", "PDF"
      ],
      selectedReportType: "",
      myContacts: {},
      myPendingContacts: {},
      selectedIbanForTransfer: "",
      toTransferIban: "",
      sendingMoney: false,
      transferAmount: 0,
      processingTheTransfer: false,
      deposits: {}
    }
  },

  computed: {

    imageLink() {
      return 'http://localhost:3077/images/' + this.details.profileImageName;
    },

    myIbans() {
      return this.cards.map(card => card.iban);
    }

  },

  methods: {
    changeViewToCards() {
      this.view = 'cards';
      CardsService.getCards()
          .then(response => {
            this.cards = response;
            console.log(response)
          });
    },

    changeViewToTransfers() {
      this.view = 'transfers'
    },

    changeViewToDeposits() {
      this.view = 'deposits'

      DepositsService.getMyDeposits()
          .then(response => this.deposits = response);
    },

    changeViewToContacts() {
      this.view = 'contacts';

      ContactsService.getMyContacts()
          .then(response => {
            this.myContacts = { ...response, selectedIban: "" };
          });
      ContactsService.getMyPendingContacts()
          .then(response => {
            this.myPendingContacts = response;
          });
    },

    getCurrencyFromIban(iban) {
      return "RON"//iban.match(/BTRL(.*)CRT/g)[0].replace("BTRL", "").replace("CRT", "");
    },

    saveCardsReport() {
      ReportsService.getCardsReport(this.selectedReportType);
    },

    startTransfer(iban) {
      if (!!iban) {
        this.toTransferIban = iban;
        this.sendingMoney = true;
      }
    },

    startTransferProcess() {
      if (this.transferAmount < this.cards.find(card => card.iban === this.selectedIbanForTransfer).currentAmount) {
        this.processingTheTransfer = true;
        ContactsService.sendMoney({
          "cardNumber": this.cards.find(card => card.iban === this.selectedIbanForTransfer).number,
          "ibanToTransfer": this.toTransferIban,
          "amount": this.transferAmount,
        })
            .finally(() => {
              this.sendingMoney = false;
              this.changeViewToCards();
            })
      }
    }

  },

  mounted() {
    console.log("intra si pa aci")
    AuthService.getDetails()
        .then(response => this.details = response)
        .catch(() => this.$router.push('/'));
    this.changeViewToCards();
  }

}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.drawer-content {
  width: 100%;
  height: 100%;
  display: none;
}

.drawer {
  width: 1vw;
  height: 100vh;
}

.drawer:hover {
  width: 20vw;
  transition: width 0.3s;
  transition-timing-function: ease-in-out;
}

.content {
  width: 99vw;
  height: 100vh;
  overflow: scroll;
  overflow-x: hidden;
}

.drawer:hover .content {
  width: 80vw;
}

.drawer:hover .drawer-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-x: hidden;
  overflow-y: scroll;
}

img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}

</style>