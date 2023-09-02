package jp.sample.vertx1.models.enumeration;

/** Http Status Code */
public enum HttpStatus {
  /** 100 Continue */
  CONTINUE(100, "Continue"),

  /** 101 Switching Protocols. */
  SWITCHING_PROTOCOLS(101, "Switching Protocols"),

  /** 102 Processing. */
  PROCESSING(102, "Processing"),

  /** 103 Early Hints. */
  EARLY_HINTS(103, "Early Hints"),

  /** 200 OK. */
  OK(200, "OK"),

  /** 201 Created. */
  CREATED(201, "Created"),

  /** 202 Accepted. */
  ACCEPTED(202, "Accepted"),

  /** 203 Non-Authoritative Information. */
  NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),

  /** 204 No Content. */
  NO_CONTENT(204, "No Content"),
  // RESET_CONTENT(205), //205 Reset Content.
  // PARTIAL_CONTENT(206), //206 Partial Content.
  // MULTI_STATUS(207), //207 Multi-Status.
  // ALREADY_REPORTED(208), //208 Already Reported.
  // IM_USED(226), //226 IM Used.

  // MULTIPLE_CHOICES(300), //300 Multiple Choices.
  // MOVED_PERMANENTLY(301), //301 Moved Permanently.

  /** 302 Found. */
  FOUND(302, "Found"),
  // SEE_OTHER(303), //303 See Other.
  // NOT_MODIFIED(304), //304 Not Modified.
  // TEMPORARY_REDIRECT(307), //307 Temporary Redirect.
  // PERMANENT_REDIRECT(308), //308 Permanent Redirect.

  /** 400 Bad Request. */
  BAD_REQUEST(400, "Bad Request"),
  // UNAUTHORIZED(401), //401 Unauthorized.
  // PAYMENT_REQUIRED(402), //402 Payment Required.

  /** 403 Forbidden. */
  FORBIDDEN(403, "Forbidden"),

  /** 404 Not Found. */
  NOT_FOUND(404, "Not Found"),

  /** 405 Method Not Allowed. */
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  // NOT_ACCEPTABLE(406), //406 Not Acceptable.
  // PROXY_AUTHENTICATION_REQUIRED(407), //407 Proxy Authentication Required.

  /** 408 Request Timeout. */
  REQUEST_TIMEOUT(408, "Request Timeout"),

  /** 409 Conflict. */
  CONFLICT(409, "Conflict"),
  // GONE(410), //410 Gone.
  // LENGTH_REQUIRED(411), //411 Length Required.
  // PRECONDITION_FAILED(412), //412 Precondition failed.
  // PAYLOAD_TOO_LARGE(413), //413 Payload Too Large.
  // URI_TOO_LONG(414), //414 URI Too Long.
  // UNSUPPORTED_MEDIA_TYPE(415), //415 Unsupported Media Type.
  // REQUESTED_RANGE_NOT_SATISFIABLE(416), //416 Requested Range Not Satisfiable.
  // EXPECTATION_FAILED(417), //417 Expectation Failed.

  // UNPROCESSABLE_ENTITY(422), //422 Unprocessable Entity.
  // LOCKED(423), //423 Locked.
  // FAILED_DEPENDENCY(424), //424 Failed Dependency.
  // TOO_EARLY(425), //425 Too Early.
  // UPGRADE_REQUIRED(426), //426 Upgrade Required.
  // PRECONDITION_REQUIRED(428), //428 Precondition Required.
  // TOO_MANY_REQUESTS(429), //429 Too Many Requests.
  // REQUEST_HEADER_FIELDS_TOO_LARGE(431), //431 Request Header Fields Too Large.
  // UNAVAILABLE_FOR_LEGAL_REASONS(451), //451 Unavailable For Legal Reasons.

  /** 500 Internal Server Error. */
  INTERNAL_SERVER_ERROR(500, "Internal Server Error");
  // NOT_IMPLEMENTED(501), //501 Not Implemented.
  // BAD_GATEWAY(502), //502 Bad Gateway.
  // SERVICE_UNAVAILABLE(503), //503 Service Unavailable.
  // GATEWAY_TIMEOUT(504), //504 Gateway Timeout.
  // HTTP_VERSION_NOT_SUPPORTED(505), //505 HTTP Version Not Supported.
  // VARIANT_ALSO_NEGOTIATES(506), //506 Variant Also Negotiates
  // INSUFFICIENT_STORAGE(507), //507 Insufficient Storage
  // LOOP_DETECTED(508), //508 Loop Detected
  // BANDWIDTH_LIMIT_EXCEEDED(509), //509 Bandwidth Limit Exceeded
  // NOT_EXTENDED(510), //510 Not Extended
  // NETWORK_AUTHENTICATION_REQUIRED(511); //511 Network Authentication Required.

  private int id; // フィールドの定義
  private String message;

  private HttpStatus(int id, String message) { // コンストラクタの定義
    this.id = id;
    this.message = message;
  }

  public int code() {
    return this.id;
  }

  public String message() {
    return this.message;
  }
}
