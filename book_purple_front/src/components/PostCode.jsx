import React from 'react';
import '../assets/css/login/postCode.css';
import { createPortal } from 'react-dom';
import DaumPostcode from 'react-daum-postcode';

function PostCode({ isOpen, onComplete, onClose }) {

    const handleComplete = (data) => {
        let fullAddr = data.roadAddress;
        let extraAddr = '';

        // 법정동
        if (data.bname && /[동|로|가]$/g.test(data.bname)) {
            extraAddr += data.bname;
        }

        // 아파트명 또는 빌딩명
        if (data.buildingName) {
            extraAddr += (extraAddr ? ', ' : '') + data.buildingName;
        }

        if (extraAddr) {
            fullAddr += ` (${extraAddr})`;
        }

        // 부모 컴포넌트로 주소 전달
        onComplete(fullAddr);
    }

    if (!isOpen) return null;

    return createPortal(
        <div className='post-code-box' onClick={onClose}>
            <div className='post-code' onClick={(e) => e.stopPropagation()}>
                <DaumPostcode
                    onComplete={handleComplete}
                    autoClose={false}
                    style={{
                        width: '100%',
                        height: '100%'
                    }}
                />
            </div>
        </div>,
        document.body
    );
}

export default PostCode;