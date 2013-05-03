$(function () {

    // if the function argument is given to overlay,
    // it is assumed to be the onBeforeLoad event listener
    $("a[rel]").overlay({

        mask: '#000',
        effect: 'apple',

        onBeforeLoad: function () {

            // grab wrapper element inside content
            var wrap = this.getOverlay().find(".contentWrap");

            // load the page specified in the trigger
            wrap.load(this.getTrigger().attr("href"));
        }

    });

    $("label[rel]").overlay({

        mask: '#000',
        effect: 'apple',
        closeOnClick: false,
        closeOnEsc: false,

        onBeforeLoad: function () {

            // grab wrapper element inside content
            var wrap = this.getOverlay().find(".contentWrap");

            // load the page specified in the trigger
            wrap.load(this.getTrigger().attr("href"));
        }

    });

    
});