import { UserConfigExport } from "vite";
import reactRefresh from "@vitejs/plugin-react-refresh";
import tailwindcss from "tailwindcss";
import autoprefixer from "autoprefixer";
import path from "path";
import { viteMockServe } from "vite-plugin-mock";

export default (): UserConfigExport => ({
  plugins: [
    reactRefresh(), 
    viteMockServe({
      mockPath: "src/mock",
      localEnabled: false,
      logger: true,
    })
  ],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  server: {
      host: "localhost",
      port: 4000,
      https: false,
      cors: true,
      proxy: {
        "/api": {
          target: "http://localhost:9999",
          changeOrigin: true,
          rewrite: path => path.replace(/^\/api/, ""),
        },
      },
    },
  css: {
    postcss: {
      plugins: [
        tailwindcss,
        autoprefixer
      ]
    }
  }
});