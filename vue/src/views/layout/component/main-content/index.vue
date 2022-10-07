<template>
    <div class="main-container">
        <TopAside/>
        <Content/>
    </div>
</template>

<script>
import TopAside from './top-aside'
import Content from './content'

export default {
    data() {
        return {}
    },
    computed: {
    },
    components: {
        TopAside,
        Content
    }
}
</script>
