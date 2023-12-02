import React, { useState, useRef, useContext, useEffect } from "react";
import { FaPencil } from "react-icons/fa6";
import { Routes, Route, Link, useParams, useNavigate } from "react-router-dom";
import { editReply } from "../utils/client";
import { UsernameContext } from '../context/UsernameContext';

const ReplyItem = ({ replyId, postId, content, edit, onDelete, setReplies }) => {
    const [editMode, setEditMode] = useState(false);
    const contentRef = useRef(null);
    const navigate = useNavigate()
    const { Username } = useContext(UsernameContext);
    const handleCancel = () => {
        setEditMode(false);
    }
    /*useEffect(() => {
        console.log('Username changed:', Username);
    }, [Username]);*/

    const handleSubmit = async (replyId) => {
        const content = contentRef.current.value;
        if (content === '') {
            setEditMode(false);
        }
        try {
            const response = await editReply(Username, replyId, content);
            if (response.status === 201) {
                setEditMode(false);
                setReplies((prevReplies) => prevReplies.map(reply =>
                    reply.id === replyId ? {
                        id: replyId,
                        content: content,
                        edit: true
                    } : reply
                ));
                navigate(`/discuss_forum/view/${postId}`);
            }

        } catch (error) {
            console.error('Error deleting reply:', error);
        }

    }


    const handleDelete = async () => {
        try {
            await onDelete(replyId);
            setEditMode(false);
        } catch (error) {
            console.error('Error deleting reply:', error);
        }
    };

    return (
        <>
            {editMode ? (
                <>
                    <form id="inputArea">
                        <div className="w-full mb-4 mt-2 border-2 border-blue-300 rounded-lg bg-gray-50">

                            <textarea rows="3"
                                className="!outline-none w-full p-2 text-medium text-gray-900 bg-white rounded-lg"
                                ref={contentRef}
                                defaultValue={content}
                            >
                            </textarea>

                            <div className="relative items-right px-3 py-1">
                                <button
                                    type="button"
                                    className="px-9 py-1 bg-green-400 rounded-lg font-sm text-white hover:bg-green-600"
                                    onClick={() => {
                                        handleSubmit(replyId)
                                    }}
                                >
                                    Submit
                                </button>
                                <button
                                    className="ml-3 px-9 py-1 bg-gray-400 rounded-lg font-sm text-white hover:bg-gray-700"
                                    onClick={handleCancel}
                                >
                                    Cancel
                                </button>
                                <button
                                    type="button"
                                    className="absolute right-2 ml-3 px-9 py-1 bg-red-400 rounded-lg font-sm text-white hover:bg-red-700"
                                    onClick={handleDelete}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    </form>
                </>
            ) :
                (
                    <div className="relative w-full p-3 my-5 bg-gray-50 border border-gray-200 rounded-lg hover:bg-gray-100">

                        <h5 className="mb-1 text-medium font-medium tracking-tight text-gray-900">{content}</h5>
                        {edit &&
                            (<button
                                className="absolute end-1.5 bottom-3 items-center px-1.5 py-1 text-medium font-medium text-center text-gray-800 bg-blue-200 rounded-lg hover:bg-blue-300 focus:ring-4 focus:outline-none focus:ring-blue-300"
                                onClick={() => setEditMode(true)}
                            >
                                <FaPencil size={15} className='inline mr-1.5' />
                                edit / delete
                            </button>)
                        }
                    </div>
                )}

        </>
    );
};

export default ReplyItem;
