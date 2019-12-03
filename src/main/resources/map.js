function initialize() {
                var myOptions = {
                      zoom: 13,
                      center: new google.maps.LatLng(41.9803467, -87.7191019),
                      mapTypeId: google.maps.MapTypeId.ROADMAP,
                      mapTypeControl: false,
                      navigationControl: false,
                      streetViewControl: false,
                      backgroundColor: "#666970"
                    };
                var map = new google.maps.Map(document.getElementById("googleMap"),myOptions);

                /*var latLng = new google.maps.LatLng(41.95, -87.7);
                var marker = new google.maps.Marker({
                    position: latLng,
                    map: map
                });*/

           }

function showCrimesOnMap(cr, centerAddress){
        let domElement = document.querySelector('#dummyBtn');
        domElement.innerHTML = `<h1>Hi Beth!</h1><div><small>I am a div that will be eliminated after all testing is done!</small></div> <br/><pre styles="width:100%;"> ${cr} </pre> <br/><ol>`;
        
        const crimes = JSON.parse(cr);
        const addre = JSON.parse(centerAddress);
        domElement.innerHTML+= `<h1>${addre.lat} ${addre.long}</h1>`;
        let mapOptions = {
            zoom: 13,
            center: new google.maps.LatLng(addre.lat, addre.long), //41.95, -87.7),
            mapTypeId: google.maps.MapTypeId.ROADMAP,
          };
        map = new google.maps.Map(document.getElementById("googleMap"),mapOptions);
        
        crimes.forEach((c,i) => {
            let crimeInfo = crimeInfoWindow(c,i);
            let marker = new google.maps.Marker({
                position: new google.maps.LatLng(c.address.lat, c.address.long),
                label: (i+1)+"",
                zIndex: (-1)*(i+1),
                map: map
            });
            let infoWindow = new google.maps.InfoWindow({
                content: crimeInfo
            });
            marker.addListener('click', (e) => {
                infoWindow.open(map, marker);
            });
            
            domElement.innerHTML += crimeInfo;
        });
        domElement.innerHTML += '</ol>';
    }

function crimeInfoWindow(c, i){
    return `<span> (${i+1}) </span>
    <h2>${c.type}</h2>
    <h3>${c.typeDescription}</h3>
    <ul>
        <li>Address: ${c.address.block + c.address.street} </li>
        <li>LatLong: (${c.address.lat}, ${c.address.long}) </li>
        <li>Date: ${c.date} </li>
    </ul>`;//`<h2>test here ${i+1}</h2> <ol>` + crimeInfo + `</ol>`
}