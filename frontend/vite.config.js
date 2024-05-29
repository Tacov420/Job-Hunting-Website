import { defineConfig } from 'vite'
import "dotenv/config";


import react from '@vitejs/plugin-react'

if (process.env.VITE_API_URL === undefined) {
  throw new Error("the environment variable VITE_API_URL is not defined");
}

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],

})
