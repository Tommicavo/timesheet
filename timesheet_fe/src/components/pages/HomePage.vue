<script>
export default {
  name: "HomePage",
  data() {
    return {};
  },
  methods: {
    async getTimesheet(task) {
      const endpoint = "/timesheets/1";
      try {
        this.$genericStore.isLoading = true;
        const res = await this.$axios.get(endpoint);
        if (task == "open") {
          this.$utilStore.openTimesheetFileInNewWindow(res.data);
        } else if (task == "download") {
          this.$utilStore.downloadTimesheetAsPDF(res.data);
        }
      } catch (err) {
        console.error(err);
      } finally {
        this.$genericStore.isLoading = false;
      }
    },
  },
};
</script>

<template>
  <h3 class="text-center py-3">Home Page</h3>
  <div ref="timesheetContent"></div>
  <button class="btn btn-primary mx-2" @click="getTimesheet('open')">Open Timesheet</button>
  <button class="btn btn-primary mx-2" @click="getTimesheet('download')">Download PDF</button>
</template>

<style lang="scss" scoped></style>
