<template>
    <div class="footer">
      <p>
        ©Copyright © 2007 - 2019 神州优车集团 All Rights Reserved
      </p>
    </div>
</template>

<script>
    export default {
        name: "footer"
    }
</script>

<style scoped>
  *{
    padding: 0;
    margin: 0;
  }*
   .footer{
     margin-top: 20px;
     background:#222c3a;
     width: 100%;
     height: 50px;
     text-align: center;
     color: white;
     position: absolute;
   }
  .footer a{
    color: #f0f1f1;
    text-decoration: none;
    padding: 0 10px;
    vertical-align:middle;
  }
  .footer p{
    height: 15px;
    color: #888888;
    margin-top: 10px;
  }
</style>
