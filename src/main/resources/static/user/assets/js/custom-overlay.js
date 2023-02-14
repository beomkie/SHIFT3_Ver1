// 3. 사용자 정의 오버레이
var CustomOverlay = function (options) {
    // 마커 이미지 크기이다. draw 메서드에서 마커 위치를 잡을 때 사용한다.
    this._imgSize = new naver.maps.Size(22, 30);

    // 오버레이에서 필요한 엘리먼트를 준비한다.
    this._prepareDOM(options.shop);
    this._shop = options.shop;

    this.setPosition(options.position);

    // 지도와 오버레이를 연결한다.
    this.setMap(options.map || null);
};

CustomOverlay.prototype = new naver.maps.OverlayView();
CustomOverlay.prototype.constructor = CustomOverlay;

CustomOverlay.prototype.setPosition = function (position) {
    this._position = position;

    // Setter 메서드를 이용해 좌표가 변경되면 오버레이 엘리먼트 위치를 다시 잡을 수 있도록 draw 메서드를 호출한다.
    this.draw();
};

CustomOverlay.prototype.getPosition = function () {
    return this._position;
};

CustomOverlay.prototype.onAdd = function () {
    // 오버레이가 위치할 페인을 결정한다. 이 예제에서는 정보 창을 올리는 floatPane을 사용한다.
    var paneName = 'floatPane',
        floatPane = this.getPanes()[paneName];

    // 오버레이 엘리먼트를 페인에 추가한다.
    this._element.appendTo(floatPane);
    // 이벤트 리스너를 등록한다.
    this._bindEvent();
};

CustomOverlay.prototype.draw = function () {
    if (!this.getMap()) {
        return;
    }

    var projection = this.getProjection(), // 오버레이 엘리먼트의 위치를 잡기 위해 프로젝션을 가져온다.
        position = this.getPosition(), // 오버레이 위치를 가져온다(위/경도).
        pixelPosition = projection.fromCoordToOffset(position); // 평면 좌표를 화면 좌표로 변환한다.

    // 화면좌표를 그대로 사용하면 마커 이미지 기준 (0, 0)이 위치한다.
    // 마커의 가운데 아래쪽 뽀족한 부분을 위치로 사용하려면 그만큼 엘리먼트를 움직여야 한다.
    this._element.css('left', pixelPosition.x - (this._imgSize.width / 2));
    this._element.css('top', pixelPosition.y - this._imgSize.height);
};

CustomOverlay.prototype.onRemove = function () {
    // 등록된 이벤트를 해제한다.
    this._unbindEvent();
    // 추가된 엘리먼트를 제거한다.
    this._element.remove();
};

CustomOverlay.prototype._onClick = function () {
    this._iw.show();
};

CustomOverlay.prototype._onClear = function () {
    this._iw.hide();
};

CustomOverlay.prototype._onEnter = function () {
    this._tooltip.show();
};

CustomOverlay.prototype._onLeave = function () {
    this._tooltip.hide();
};

CustomOverlay.prototype._bindEvent = function () {
    this._element
        .on('click', 'img', this._onClick.bind(this))
        .on('click', '.btn-clear', this._onClear.bind(this))
        .on('mouseenter', 'img', this._onEnter.bind(this))
        .on('mouseout', 'img', this._onLeave.bind(this));
};

CustomOverlay.prototype._unbindEvent = function () {
    this._element.off();
};

CustomOverlay.prototype._prepareDOM = function () {
    var markerContent =
        ``;

    this._element = $(markerContent);
    this._iw = this._element.find('.infowindow');
    this._tooltip = this._element.find('.pins_s_tooltip');
};