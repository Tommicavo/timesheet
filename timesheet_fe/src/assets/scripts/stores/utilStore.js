import { defineStore } from "pinia";
import html2pdf from "html2pdf.js";

export const useUtilStore = defineStore({
  id: "util",
  state: () => ({
    timesheetContent: "",
  }),
  actions: {
    openTimesheetFileInNewWindow(data) {
      if (data && data.blobTimesheet) {
        // Decode base64 to a binary string
        const binaryString = window.atob(data.blobTimesheet);

        // Convert the binary string to a Uint8Array
        const bytes = new Uint8Array(binaryString.length);
        for (let i = 0; i < binaryString.length; i++) {
          bytes[i] = binaryString.charCodeAt(i);
        }

        // Create a Blob from the bytes
        const blob = new Blob([bytes], { type: "text/html" });

        // Create a URL for the Blob
        const url = window.URL.createObjectURL(blob);

        // Open the URL in a new tab
        window.open(url, "_blank");

        // Clean up
        window.URL.revokeObjectURL(url);
      }
    },
    downloadTimesheetAsPDF(data) {
      const opt = {
        margin: 0,
        filename: `${data.titleTimesheet}.pdf`,
        image: { type: "jpeg", quality: 0.98 },
        html2canvas: {
          scale: 5,
        },
        jsPDF: {
          unit: "mm",
          format: [210, 297.1], // for preventing filling the entire page and make html2pdf create a new blank page
          orientation: "portrait",
          margin: 0,
        },
      };

      // Convert the byte array to a string
      const htmlString = window.atob(data.blobTimesheet);

      // Using html2pdf library to convert HTML to PDF
      html2pdf().from(htmlString).set(opt).save();
    },
  },
});
