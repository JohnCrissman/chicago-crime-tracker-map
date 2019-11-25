function initialize() {
                var myOptions = {
                      zoom: 14,
                      center: new google.maps.LatLng(41.95, -87.7),
                      mapTypeId: google.maps.MapTypeId.ROADMAP,
                      mapTypeControl: false,
                      navigationControl: false,
                      streetViewControl: false,
                      backgroundColor: "#666970"
                    };
                var map = new google.maps.Map(document.getElementById("googleMap"),myOptions);
           }