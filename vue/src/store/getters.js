const getters={
    token: state=> state.user.token,
    username: state=>state.user.username,
    phone: state=>state.user.phone,
    roles: state=>state.user.roles,
    permissions: state=>state.user.permissions
}

export default getters
