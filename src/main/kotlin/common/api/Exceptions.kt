package common.api

class NotificationInsertFailedException(override val message: String) : RuntimeException()
class NotificationUpdateFailedException(override val message: String) : RuntimeException()
class NotificationNotFoundException(override val message: String) : RuntimeException()
class NotificationMessageTooLongException(override val message: String) : RuntimeException()
class NotificationInvalidUserIdException(override val message: String) : RuntimeException()