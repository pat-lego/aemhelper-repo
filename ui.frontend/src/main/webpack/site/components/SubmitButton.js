global.support.components.SubmitButton = {
    template: `<button id="submit" @click="clickEvent">{{name}}</button>`,
    props: ['name'],
    methods: {
        clickEvent() {
            this.$emit('submit-button')
        }
    }
}
