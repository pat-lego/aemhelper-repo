
    use(function() {
        'use strict';

        var colour = this.colour || '';
        var year = this.year || new Date().getFullYear();

        return {
            colour: colour,
            year: year
        }
    });