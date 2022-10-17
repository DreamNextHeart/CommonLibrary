import Vuex from 'vuex'
import user from './modules/user'
import getters from './getters'
import permission from "@/store/modules/permission";

// Vue.use(Vuex)

const store=new Vuex.Store({
    modules: {
        user,
        permission
    },
    getters
})
export default store
