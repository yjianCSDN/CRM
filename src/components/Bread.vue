<template>
  <div>
      <span style="font-size: 22px">{{name}}</span>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Bread",
  data(){
    return{
      breadList: [],
      nameList:[],
      name:""
    }
  },
  mounted() {
    // console.log(this.$route)
    this.name = this.$route.meta.title
    // console.log("name:",this.name)
  },
  methods:{
  },
  watch:{
    $route:{
      // eslint-disable-next-line no-unused-vars
      handler(route) {
        // console.log("this.route::::", route)
       this.name = route.meta.title
      }
    },
  }
}
</script>

<style scoped>

</style>