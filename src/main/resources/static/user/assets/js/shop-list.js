// window.initMap = function () {
//
//     let mapDiv = document.getElementById('map');
//     window.map = new naver.maps.Map(mapDiv, {
//         zoom: 19,
//         minZoom: 1,
//     });
// }

window.infowindow = [];
const htmlMarker1 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(' + "/user/assets/images/cluster-marker-1.png" + ');background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker2 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(' + "/user/assets/images/cluster-marker-2.png" + ');background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker3 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(' + "/user/assets/images/cluster-marker-3.png" + ');background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker4 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(' + "/user/assets/images/cluster-marker-4.png" + ');background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    },
    htmlMarker5 = {
        content: '<div style="cursor:pointer;width:40px;height:40px;line-height:42px;font-size:10px;color:white;text-align:center;font-weight:bold;background:url(' + "/user/assets/images/cluster-marker-5.png" + ');background-size:contain;"></div>',
        size: N.Size(40, 40),
        anchor: N.Point(20, 20)
    };

window.markerClusterer = new MarkerClustering({
    minClusterSize: 2,
    maxZoom: 11,
    disableClickZoom: false,
    gridSize: 130,
    icons: [htmlMarker1, htmlMarker2, htmlMarker3, htmlMarker4, htmlMarker5],
    indexGenerator: [5, 10, 30, 50, 100],
    stylingFunction: function (clusterMarker, count) {
        $(clusterMarker.getElement()).find('div:first-child').text(count);
    }
});

window.removeAllMarker = function () {
    infowindow.forEach(function (e) {
        let ele = e.getElement();
        $(ele).remove();
    });

    window.infowindow = [];
}

window.drawMap = function (shop) {
    let lat = shop.latitude;
    let lng = shop.longitude;
    let position = new naver.maps.LatLng(lat, lng);

    var markerContent =
            `<div style="position:absolute;">
            <div class="infowindow" style="
                    font-size: 0.6rem;
                    padding: 0.8rem; display:none;position:absolute;
                    width: 13rem;
                    height: 4rem;
                    top: -5rem;
                    left: -6rem;
                    background-color:white;z-index:1;
                    border-radius: 1.3em;">
                <a class="float-end btn-close"></a>
                <div class="row g-0">
                    <div class="col-4">
                    ${(shop.imageSelectShops.length !== 0) ?
                `<img src="/file/${shop.imageSelectShops[0].imageName}" alt="편집샵 미리보기" class="thumbnail"/>` :
                `<img src="https://icon-library.com/images/no-picture-available-icon/no-picture-available-icon-20.jpg" class="thumbnail" alt="no-image">`}
                    </div>
                    <div class="col-8" style="align-self: center;    padding-left: 0.2rem;">
                        <span style="word-break: keep-all">${shop.name}</span>
                    </div>
                </div>
            </div>
            <div class="pin_s" style="cursor: pointer; width: 22px; height: 30px;">
                <img src="/user/assets/images/pin.png" alt="" style="margin: 0px; padding: 0px; border: 0px solid transparent; display: block; max-width: none; max-height: none; -webkit-user-select: none; position: absolute; width: 30px; height: 30px; left: 0px; top: 0px;">
            </div> 
        </div>`,

        htmlMarker = new naver.maps.Marker({
            position: position,
            map: map,
            icon: {
                content: markerContent,
                size: new naver.maps.Size(22, 30),
                anchor: new naver.maps.Point(11, 30)
            }
        }), elHtmlMarker = htmlMarker.getElement();

    window.infowindow.push(htmlMarker);

    // markerClusterer.addMarkers(htmlMarker);

    $(elHtmlMarker).on('click', 'img', function (e) {
        if ($("#shopDetail #shopId").val() != shop.id) {
            detailShop(shop)
        }
        $("#shopDetail").show();

        $(".infowindow").hide();

        $(elHtmlMarker).find('.infowindow').show();
    });

    $(elHtmlMarker).on('click', '.btn-close', function (e) {
        $(elHtmlMarker).find('.infowindow').hide();
    });

    var overlay = new CustomOverlay({
        map: map,
        position: position,
        shop: shop,
    });

    // when marker click, toggle overlay
    naver.maps.Event.addListener(htmlMarker, 'click', function (e) {
        if (overlay.getMap()) {
            overlay.setMap(null);
        } else {
            overlay.setMap(map);
        }
    });

    /*// 지도 줌 레벨에 따라 클러스터링이 활성화/비활성화되도록 설정합니다.
    naver.maps.Event.addListener(map, 'zoom_changed', function () {
        var level = map.getZoom();
        if (level <= 13) {
            markerClusterer.setMap(map);
        } else {
            markerClusterer.setMap(null);
        }
    });*/

    map.setZoom(13);
    map.panTo(position);
}

window.clusteringMap = function () {
    window.markerClusterer.setOptions({
        markers: infowindow
    });
}
// initMap();