# EmailPoller

Email Poller is the process of automatically reading the unread emails from specified email account in certain period of interval to perform some operation dynamically.

**Email Poller is available in below location**
  
    https://github.com/ran-jit/EmailPoller/wiki

## How it works
The following inputs are required for email polling actions,
  1. Protocol - Email server protocol - ex: "imaps"
  2. Hostname - Email server host name - ex: "imap.gmail.com"
  3. Username - Email account username
  4. Password - Email account password
  5. Look-up Folder - The folder to reading the emails - ex: "INBOX"
  6. TLS Enabled - Email TLS configuration (default: false)
  7. Email Message Action Factory – Factory to process the email message actions

Based on the above credentials, email poller system reads the unread email messages from the specified account and forward the request to Email Message Action Factory and the factory.

Email Message Action Factory is to define the email actions based on the custom filters (example: Email subject contains "Process") to assign the filter handler. Since using the handler, the email poller process the request operation/post action.

#### Example:
  * Examples are committed in test package.

#### Note:
  * The POP3 server does not keep the email flag (read/unread) information. Whenever we are initiating the search request in email server, returns all the email messages. Due to this reason, the email poller system supports only IMAP server accounts.
