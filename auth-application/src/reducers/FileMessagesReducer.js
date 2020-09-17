
export default function (state = [], action) {
    switch (action.type) {
        case 'FILE_MESSAGE':
            return [...state, action.fileInfo];
        default:
    }
    return state;
}
