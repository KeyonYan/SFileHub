import { defineConfig } from "vite";
import reactRefresh from "@vitejs/plugin-react-refresh";
import * as path from "path";
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [reactRefresh()],
  // 配置路径别名
      resolve: {
        alias: {
          "@": path.resolve(__dirname, "src"),
        },
      },
    server: {
        port: 3000,
        proxy: {
          "/api": {
            target: "https://yourBaseUrl",
            changeOrigin: true,
            cookieDomainRewrite: "",
            secure: false,
          },
        },
      },
});