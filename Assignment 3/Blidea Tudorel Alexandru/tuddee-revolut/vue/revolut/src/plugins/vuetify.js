// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'

// Vuetify
import { createVuetify } from 'vuetify'

export default createVuetify(
    {
        theme: {
            themes: {
                light: {
                    primary: "#1459a9",
                    secondary: "#67b6fe",
                    accent: "#fb86a9",
                    success: "#a8e4db"
                }
            }
        }
    }
  // https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
)
