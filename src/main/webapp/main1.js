function tab() {
    let tabNav = document.querySelectorAll('.tabs-nav__item'),
        tabContent = document.querySelectorAll('.tab'),
        tabName = getCookie("tabName");
    tabNav.forEach(item => {
        if (item.getAttribute('data-tab-name') === tabName) {

            tabNav.forEach(item => {
                item.classList.remove('is-active');
            });
            item.classList.add('is-active');
        }
    })
    selectTabContent(tabName, tabContent);
    tabNav.forEach(item => {
        item.addEventListener('click', selectTabNav)
    });

    function selectTabNav() {
        tabNav.forEach(item => {
            item.classList.remove('is-active');
        });
        this.classList.add('is-active');
        tabName = this.getAttribute('data-tab-name');
        document.cookie = "tabName=" + tabName;
        selectTabContent(tabName, tabContent);
    }
};

function selectTabContent(tabName, tabContent) {
    tabContent.forEach(item => {
        item.classList.contains(tabName) ? item.classList.add('is-active') : item.classList.remove('is-active');
    })
}

function showBalanceForm() {

    let request = new XMLHttpRequest();
    let sum = prompt("Введите сумму пополнения", "0");
    if (parseFloat(sum)) {
        let body = "sum=" + sum;
        request.open("POST", "http://localhost:8080/webCustomerView.jsp?" + body);
        request.onreadystatechange = reqReadyStateChange(request);
        request.send();
    } else {
        alert("Введите число");
    }
}

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function reqReadyStateChange(request) {
    if (request.readyState === 4) {
        let status = request.status;
        if (status === 200) {
            document.getElementById("output").innerHTML = request.responseText;
        }
    }
}

function changeArea(services, templates) {
    let select = document.getElementById('areaChange');
    let areaId = select.options[select.selectedIndex].value;


    let disServices = services.filter(function (item,i,arr){
        let res = true;
        templates[item['id']]['possibleAreasId'].forEach(function (item1) {
            if(item1 == areaId){
                res = false;
            }
        });
        return res;
    });

    let res='Will disconnect:\n';
    disServices.forEach(item=>res+=templates[item['id']]['name']+' '+item['status']+'\n');

    alert(res);
}


tab();