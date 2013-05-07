

function rafraichir() {
    window.opener.location.reload();
}

function majuscule(zone) {
    zone.value = zone.value.toUpperCase();
}

 function regexNom(obj)
{
    var codeascii;
    /*codeascii = applyKey();*/


    if ((codeascii != 37)) {
        if ((codeascii != 39)) {
            var minus = "aàâäbcçdeéèêëfghiîïjklmnoôöpqrstuùûvwxyz²- 0123456789&+',:!.;(\"()[]"
            var majus = "aaaabccdeeeeefghiiijklmnooopqrstuuuvwxyz________________________"
            var entree = obj.value;
            var sortie = "";
            for (var i = 0; i < entree.length; i++) {
                var car = entree.substr(i, 1);
                sortie += (minus.indexOf(car) != -1) ? majus.substr(minus.indexOf(car), 1) : car;
            }

            obj.value = sortie;
        }
    }

}

function regexPrenom(obj) {
    var codeascii;
    /*codeascii = applyKey();*/


    if ((codeascii != 37)) {
        if ((codeascii != 39)) {
            var minus = "aàâäbcçdeéèêëfghiîïjklmnoôöpqrstuùûvwxyz²_ 0123456789&+',:!.;(\"()[]"
            var majus = "aaaabccdeeeeefghiiijklmnooopqrstuuuvwxyz------------------------"
            var entree = obj.value;
            var sortie = "";
            for (var i = 0; i < entree.length; i++) {
                var car = entree.substr(i, 1);
                sortie += (minus.indexOf(car) != -1) ? majus.substr(minus.indexOf(car), 1) : car;
            }

            obj.value = sortie;
        }
    }

}

function regexLogin(obj) {
    var codeascii;
    /*codeascii = applyKey();*/


    if ((codeascii != 37)) {
        if ((codeascii != 39)) {
            var minus = "aàâäbcçdeéèêëfghiîïjklmnoôöpqrstuùûvwxyz²- 0123456789&+',:!.;(\"_()[]"
            var majus = "aaaabccdeeeeefghiiijklmnooopqrstuuuvwxyz"
            var entree = obj.value;
            var sortie = "";
            for (var i = 0; i < entree.length; i++) {
                var car = entree.substr(i, 1);
                sortie += (minus.indexOf(car) != -1) ? majus.substr(minus.indexOf(car), 1) : car;
            }

            obj.value = sortie;
        }
    }

}