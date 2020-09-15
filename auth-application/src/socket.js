
const AppConfig = {
    PROTOCOL: "wss:",
    HOST: "ws://localhost:9000",
    PORT: ":9000"
}

const Singleton = (function () {
    let instance;

    function createInstance() {
        const socket = new WebSocket(AppConfig.HOST);
        return socket;
    }

    return {
        getInstance: function () {
            if (!instance) {
                instance = createInstance();
            }
            return instance;
        }
    };
})();

export default Singleton;