import { defineConfig } from "vite";
import reactRefresh from "@vitejs/plugin-react-refresh";
import * as path from "path";
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [reactRefresh()],
  server: {
      host: "localhost",
      port: 3000,
      https: false,
      proxy: {
        "/api": {
          target: "http://localhost:9999/sfilehub",
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, ""),
        },
      },
    },
});