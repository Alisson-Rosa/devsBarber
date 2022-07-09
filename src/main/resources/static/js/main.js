// $('.slider-principal').slick({
//     dots: true,
//     infinite: true,
//     speed: 300,
//     slidesToShow: 1,
//     adaptiveHeight: true,
//     autoplay: true,
//     autoplaySpeed: 2000
// });

// const button = document.getElementById('btnSubmit');

function validatorInpust(idInput){
    var input = document.getElementById(idInput);

    if(input.value === ''){
        setErrorFor(input, 'Esse campo deve ser preenchido!');
    } else {
        setSuccessFor(input)
    }

    if(input.id === 'password'){
        validatorPassword(input);
    }

    if(input.id === 'email'){
        validatorEmail(input)
    }

    if(input.id === 'telephone'){
        validatorTelephone(input);
    }

    if(input.id === 'birthdate'){
        validatorBirthDate(input);
    }
}

function validatorTelephone(input){
    var rgTel = /(\(\d{2}\)\s)?(\d{4,5}\-\d{4})/;

    if(rgTel.test(input.value)){
        setSuccessFor(input)
    } else {
        setErrorFor(input, 'Telefone preenchido em formato inválido');
    }
}

function validatorBirthDate(input) {
    var dateInput = new Date (input.value);

    var date = new Date();
    var month = date.getMonth() + 1;
    var day = date.getDay() + 3;
    var year = date.getFullYear() - 16;

    var stringBuilderDate = year + '/' + month + '/' + day;
    var olderAge = new Date(stringBuilderDate);
    var elder = new Date('1922/07/07');

    if(isNaN(dateInput.getDate().valueOf())){
        setErrorFor(input, "Data inválida!");
        return;
    }

    if(isNaN(dateInput.getMonth().valueOf())){
        setErrorFor(input, "Data inválida!");
        return;
    }

    if(isNaN(dateInput.getFullYear())){
        setErrorFor(input, "Data inválida!");
        return;
    }

    if(dateInput > olderAge ){
        setErrorFor(input, 'Você Precisa Ser Maior de 16 Anos!')
    } else {
        if(dateInput < elder){
            setErrorFor(input, "Data inválida!")
        } else {
            setSuccessFor(input)
        }
    }
}

function validatorEmail(input){
    const re = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    if(re.test(input.value)){
        setSuccessFor(input)
    } else {
        setErrorFor(input, 'Formato de e-mail inválido!')
    }
}

function validatorPassword(input){
    if(input.value.length < 6){
        setErrorFor(input, 'A senha deve conter mais de 6 caracteres!')
    } else {
        setSuccessFor(input);
    }
}

function checkInputs(ids, submit) {
    var result = true;
    ids.forEach(function (ids){
        var input = document.getElementById(ids);
        var btnSubimit = document.getElementById(submit);
        var formGroup = input.parentElement;
        if(formGroup.classList.contains('error')){
            setErrorFor(btnSubimit, 'Os dados estão inválidos!')
            result = false;
        }
    });

    if(!result){
        return false;
    }

    return true;
}

function setErrorFor(input, message) {
    const formGroup = input.parentElement;
    const small = formGroup.querySelector('small');

    small.innerText = message

    formGroup.className = 'form-group error'
}

function setSuccessFor(input) {
    const formGroup = input.parentElement;

    formGroup.className = 'form-group success'
}
