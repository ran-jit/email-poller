# Email Poller

Email Poller is the process of automatically reading the unread emails from specified email account in certain period of interval to perform defined actions.

more details.. https://github.com/ran-jit/email-poller/wiki

## Email Poller properties
     * Protocol             - Email server protocol                                - ex: imaps
     * Host name            - Email server host name                               - ex: imap.gmail.com
     * Port number          - Email server port number                             - ex: 143
     * TLS Enabled          - Email TLS configuration (default: false)             - ex: true
     * User name            - Email account username                               - ex: <USERNAME>
     * Password             - Email account password                               - ex: <PASSWORD>
     * Polling delay        - Email message polling delay in milliseconds          - ex: 120000
     * Look-up Folder       - The folder to reading the emails                     - ex: "INBOX"
     * Polling thread size  - Email message polling thread size (default: 1)       - ex: 2
     * Handler factory      - Email message action handler factory

Based on the above credentials, email poller system reads the unread email messages from the specified account and forward the request to Email Message Action Factory.

Email message handler action factory is to define the email actions based on the custom filters (example: Email subject contains "Process") to assign the filter handler. Since using the handler, the email poller process the request operation/post action.

## Interfaces
### EmailPoller
This is the interface where you start, stop and restart the email poller.

### MessageActionHandler
This is the interface to perform email message actions.

### MessageActionHandlerFactory
This is the interface to get email action handler instance.

#### Example:
  * Examples are committed in test package.

#### Note:
  * The POP3 server does not keep the email flag (read/unread) information. Whenever we are initiating the search request in email server, returns all the email messages. Due to this reason, the email poller system supports only IMAP server accounts.
