import Vue from 'vue'
import Router from 'vue-router'
import Index from '@/components/Index'
import LinkMgt from "@/components/LinkMgt";
import SystemConfig from "@/components/SystemConfig";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect:'LinkMgt',
      component: Index
    },
    {
      path: '/linkMgt',
      name: 'LinkMgt',
      component: LinkMgt
    },
    {
      path: '/systemConfig',
      name: 'SystemConfig',
      component: SystemConfig
    }

  ]
})
